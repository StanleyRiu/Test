package com.cht.tltc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class UpdateManager {

	public UpdateManager() {
		// TODO Auto-generated constructor stub
	}
	public static class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
            super();
    		// Load CAs from an InputStream
    		CertificateFactory cf = CertificateFactory.getInstance("X.509");
    		InputStream caInput = new BufferedInputStream(new FileInputStream("custom-keystore.crt"));
    		java.security.cert.Certificate ca = cf.generateCertificate(caInput);

    		// Create a KeyStore containing our trusted CAs
    		String keyStoreType = KeyStore.getDefaultType();
    		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
    		keyStore.load(null, null);
    		keyStore.setCertificateEntry("ca", ca);

    		// Create a TrustManager that trusts the CAs in our KeyStore
    		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
    		tmf.init(keyStore);


            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

		@Override
		public String[] getDefaultCipherSuites() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getSupportedCipherSuites() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
				throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
    }

}
