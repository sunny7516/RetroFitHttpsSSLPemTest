package com.example.tacademy.retrofithttpssslpemtest;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tacademy on 2017-02-20.
 */

public class NetSSL {
    private static NetSSL ourInstance = new NetSSL();

    public static NetSSL getInstance() {
        return ourInstance;
    }

    private NetSSL() {
    }
    ////////////////////////////////////////////////////////////
    //sslSocketFactory,hostnameVerifier에 들어갈 매개변수 내용
    //SSLSocketFactory sslSocketFactory
    //X509TrustManager x509TrustManager
    //HostnameVerifier hostnameVerifier

    SSLSocketFactory sslSocketFactory;
    X509TrustManager x509TrustManager;
    HostnameVerifier hostnameVerifier;
    OkHttpClient client;
    //////////////////////////////////////////////////////////////
    Retrofit retrofit;

    //////////////////////////////////////////////////////////
    // Context 때문에 생성 방식을 변경함.
    public void launch(Context context) {
        if (sslSocketFactory == null && x509TrustManager == null && hostnameVerifier == null) {
            try {
                sslSocketFactory = getSslSocketFactory(context);
                hostnameVerifier = getHostnameVerifier();
            } catch (Exception e) {
                e.printStackTrace();
            }
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, x509TrustManager)
                    .hostnameVerifier(hostnameVerifier)
                    .build();
            ////////////////////////////////////////////////////////////
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://ec2-52-78-39-45.ap-northeast-2.compute.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    ////////////////////////////////////////////////////////////
    // Getter
    MemberImpFactory memberImpFactory;

    public MemberImpFactory getMemberImpFactory() {
        if(memberImpFactory == null){
            memberImpFactory = retrofit.create(MemberImpFactory.class);
        }
        return memberImpFactory;
    }

    ////////////////////////////////////////////////////////////////////
    // HostnameVerifier
    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }

    ////////////////////////////////////////////////////////////////////
    // SSLSocketFactory
    private SSLSocketFactory getSslSocketFactory(Context context)
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        // 1 단계 인증 X.509를 사용하는 인증서 팩토리 객체 생성
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // 2. pem을 읽어서 인증서 설정
        InputStream caInput = context.getResources().openRawResource(R.raw.bowlingk_cert);
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        // 3. Keystore에 설정, 안드로이드 BKS, JkS를 사용할 수도 있음
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        // 4. 신뢰매니저 생성 후 키스토어 설정
        String tmAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmAlgorithm);
        tmf.init(keyStore);

        // 신뢰 매니저들 획득
        TrustManager[] trustManagers = getTrustManagers(tmf.getTrustManagers());

        // SSL 획득
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);
        return sslContext.getSocketFactory();
    }

    // 래핑처리 (TrustManager의 wrapping)
    private TrustManager[] getTrustManagers(TrustManager[] trustManagers) {
        // 래핑이 되기 전에 원본
        x509TrustManager = (X509TrustManager) trustManagers[0];
        // 체크후 리턴
        return new TrustManager[]{
                // 인증서를 클라이언트단과 서버단 체크를 해서 다시 넣어준다.
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                        //throws CertificateException { // try/catch나 throws 둘 중 하나를 사용
                        try {
                            if (x509Certificates != null && x509Certificates.length > 0) {
                                x509Certificates[0].checkValidity();
                            } else {
                                x509TrustManager.checkClientTrusted(x509Certificates, s);
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        try {
                            if (x509Certificates != null && x509Certificates.length > 0) {
                                x509Certificates[0].checkValidity();
                            } else {
                                x509TrustManager.checkClientTrusted(x509Certificates, s);
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return x509TrustManager.getAcceptedIssuers();
                    }
                }
        };
    }
}
