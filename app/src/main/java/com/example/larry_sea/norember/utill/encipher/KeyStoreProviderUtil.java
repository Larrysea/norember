package com.example.larry_sea.norember.utill.encipher;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.util.Log;

import com.example.larry_sea.norember.test.SecurityConstants;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.security.auth.x500.X500Principal;

/**
 * Created by Larry-sea on 2016/10/30.
 * <p>
 * 秘钥加密工具用来保存应用程序的秘钥
 * <p>
 * <p>
 * 第一次使用需要初始化秘钥  onCreateKey
 * <p>
 * 让后用私钥签名         sign_data
 * <p>
 * 然后验证数据          verify_data
 */

public class KeyStoreProviderUtil {


    public static final String TAG = "KeyStoreFragment";
    // BEGIN_INCLUDE(values)


    // Some sample data to sign, and later verify using the generated signature.

    // Just a handy place to store the signature in between signing and verifying.
    public String mSignatureStr = null;

    // You can store multiple key pairs in the Key Store.  The string used to refer to the Key you
    // want to store, or later pull, is referred to as an "alias" in this case, because calling it
    // a key, when you use it to retrieve a key, would just be irritating.
    private String mAlias = null;  //私钥别名


    /**
     * 生成一个秘钥
     * Creates a public and private key and stores it using the Android Key Store, so that only
     * this application will be able to access the keys.
     */
    public void createKeys(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        // BEGIN_INCLUDE(create_valid_dates)
        // Create a start and end time, for the validity range of the key pair that's about to be
        // generated.
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 1);
        //END_INCLUDE(create_valid_dates)


        // BEGIN_INCLUDE(create_spec)
        // The KeyPairGeneratorSpec object is how parameters for your key pair are passed
        // to the KeyPairGenerator.  For a fun home game, count how many classes in this sample
        // start with the phrase "KeyPair".
        KeyPairGeneratorSpec spec =
                new KeyPairGeneratorSpec.Builder(context)
                        // You'll use the alias later to retrieve the key.  It's a key for the key!
                        .setAlias(mAlias)
                        // The subject used for the self-signed certificate of the generated pair
                        .setSubject(new X500Principal("CN=" + mAlias))
                        // The serial number used for the self-signed certificate of the
                        // generated pair.
                        .setSerialNumber(BigInteger.valueOf(1337))
                        // Date range of validity for the generated pair.
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
        // END_INCLUDE(create_spec)

        // BEGIN_INCLUDE(create_keypair)
        // Initialize a KeyPair generator using the the intended algorithm (in this example, RSA
        // and the KeyStore.  This example uses the AndroidKeyStore.
        KeyPairGenerator kpGenerator = KeyPairGenerator
                .getInstance(SecurityConstants.TYPE_RSA,
                        SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
        kpGenerator.initialize(spec);
        KeyPair kp = kpGenerator.generateKeyPair();
        Log.d(TAG, "Public Key is: " + kp.getPublic().toString());
        Log.d(TAG, "Private key is" + kp.getPrivate().toString());
        // END_INCLUDE(create_keypair)
    }

    /**
     * 私钥签名
     * <p>
     * <p>
     * Signs the data using the key pair stored in the Android Key Store.  This signature can be
     * used with the data later to verify it was signed by this application.
     *
     * @return A string encoding of the data signature generated
     */
    public String signData(String inputStr) throws KeyStoreException,
            UnrecoverableEntryException, NoSuchAlgorithmException, InvalidKeyException,
            SignatureException, IOException, CertificateException {
        byte[] data = inputStr.getBytes();

        // BEGIN_INCLUDE(sign_load_keystore)
        KeyStore ks = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);

        // Weird artifact of Java API.  If you don't have an InputStream to load, you still need
        // to call "load", or it'll crash.
        ks.load(null);

        // Load the key pair from the Android Key Store
        KeyStore.Entry entry = ks.getEntry(mAlias, null);

        /* If the entry is null, keys were never stored under this alias.
         * Debug steps in this situation would be:
         * -Check the list of aliases by iterating over Keystore.aliases(), be sure the alias
         *   exists.
         * -If that's empty, verify they were both stored and pulled from the same keystore
         *   "AndroidKeyStore"
         */
        if (entry == null) {
            Log.w(TAG, "No key found under alias: " + mAlias);
            Log.w(TAG, "Exiting signData()...");
            return null;
        }

        /* If entry is not a KeyStore.PrivateKeyEntry, it might have gotten stored in a previous
         * iteration of your application that was using some other mechanism, or been overwritten
         * by something else using the same keystore with the same alias.
         * You can determine the type using entry.getClass() and debug from there.
         */
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            Log.w(TAG, "Exiting signData()...");
            return null;
        }
        // END_INCLUDE(sign_data)

        // BEGIN_INCLUDE(sign_create_signature)
        // This class doesn't actually represent the signature,
        // just the engine for creating/verifying signatures, using
        // the specified algorithm.
        Signature s = Signature.getInstance(SecurityConstants.SIGNATURE_SHA256withRSA);

        // Initialize Signature using specified private key
        s.initSign(((KeyStore.PrivateKeyEntry) entry).getPrivateKey());

        // Sign the data, store the result as a Base64 encoded String.
        s.update(data);
        byte[] signature = s.sign();
        String result = Base64.encodeToString(signature, Base64.DEFAULT);
        // END_INCLUDE(sign_data)

        return result;
    }


    /**
     * Given some data and a signature, uses the key pair stored in the Android Key Store to verify
     * that the data was signed by this application, using that key pair.
     *
     * @param input        The data to be verified.
     * @param signatureStr The signature provided for the data.
     * @return A boolean value telling you whether the signature is valid or not.
     */
    public boolean verifyData(String input, String signatureStr) throws KeyStoreException,
            CertificateException, NoSuchAlgorithmException, IOException,
            UnrecoverableEntryException, InvalidKeyException, SignatureException {
        byte[] data = input.getBytes();
        byte[] signature;
        // BEGIN_INCLUDE(decode_signature)

        // Make sure the signature string exists.  If not, bail out, nothing to do.

        if (signatureStr == null) {
            Log.w(TAG, "Invalid signature.");
            Log.w(TAG, "Exiting verifyData()...");
            return false;
        }

        try {
            // The signature is going to be examined as a byte array,
            // not as a base64 encoded string.
            signature = Base64.decode(signatureStr, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            // signatureStr wasn't null, but might not have been encoded properly.
            // It's not a valid Base64 string.
            return false;
        }
        // END_INCLUDE(decode_signature)

        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");

        // Weird artifact of Java API.  If you don't have an InputStream to load, you still need
        // to call "load", or it'll crash.
        ks.load(null);

        // Load the key pair from the Android Key Store
        KeyStore.Entry entry = ks.getEntry(mAlias, null);

        if (entry == null) {
            Log.w(TAG, "No key found under alias: " + mAlias);
            Log.w(TAG, "Exiting verifyData()...");
            return false;
        }

        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            return false;
        }

        // This class doesn't actually represent the signature,
        // just the engine for creating/verifying signatures, using
        // the specified algorithm.
        Signature s = Signature.getInstance(SecurityConstants.SIGNATURE_SHA256withRSA);

        // BEGIN_INCLUDE(verify_data)
        // Verify the data.
        s.initVerify(((KeyStore.PrivateKeyEntry) entry).getCertificate());
        s.update(data);
        return s.verify(signature);
        // END_INCLUDE(verify_data)
    }

    public void setAlias(String alias) {
        mAlias = alias;
    }


    /*
    * 获取加密公钥
    *
    * */
    public byte[] getPublicKey(Context context) throws KeyStoreException,
            CertificateException, NoSuchAlgorithmException, IOException,
            UnrecoverableEntryException, InvalidKeyException, SignatureException {
        KeyStore ks = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);

        // Weird artifact of Java API.  If you don't have an InputStream to load, you still need
        // to call "load", or it'll crash.
        ks.load(null);

        // Load the key pair from the Android Key Store
        KeyStore.Entry entry = ks.getEntry(mAlias, null);

        /* If the entry is null, keys were never stored under this alias.
         * Debug steps in this situation would be:
         * -Check the list of aliases by iterating over Keystore.aliases(), be sure the alias
         *   exists.
         * -If that's empty, verify they were both stored and pulled from the same keystore
         *   "AndroidKeyStore"
         */
        if (entry == null) {
            Log.w(TAG, "No key found under alias: " + mAlias);
            Log.w(TAG, "Exiting signData()...");
            try {
                createKeys(context);
                return getPublicKey(context);
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return null;
        }

        /* If entry is not a KeyStore.PrivateKeyEntry, it might have gotten stored in a previous
         * iteration of your application that was using some other mechanism, or been overwritten
         * by something else using the same keystore with the same alias.
         * You can determine the type using entry.getClass() and debug from there.
         */
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            Log.w(TAG, "Exiting signData()...");
            return null;
        }
        // END_INCLUDE(sign_data)

        // BEGIN_INCLUDE(sign_create_signature)
        // This class doesn't actually represent the signature,
        // just the engine for creating/verifying signatures, using
        // the specified algorithm.
        Signature s = Signature.getInstance(SecurityConstants.SIGNATURE_SHA256withRSA);
        return ((KeyStore.PrivateKeyEntry) entry).getCertificate().getPublicKey().toString().getBytes();


    }


}
