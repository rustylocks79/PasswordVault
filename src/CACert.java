import java.io.ByteArrayInputStream;
import java.security.cert.*;

public class CACert {

    CertificateFactory certFactory;
    X509Certificate x509cert;

    private String name;

    // put this string in main to make our cert
    // then the user can enter the cert's name
    // if it equals our cert and our cert is verified then its good
    private String cert =

                "-----BEGIN CERTIFICATE-----" +
        "MIIFcTCCA1mgAwIBAgIDFFZUMA0GCSqGSIb3DQEBCwUAMHkxEDAOBgNVBAoTB1Jv"+
        "b3QgQ0ExHjAcBgNVBAsTFWh0dHA6Ly93d3cuY2FjZXJ0Lm9yZzEiMCAGA1UEAxMZ"+
        "Q0EgQ2VydCBTaWduaW5nIEF1dGhvcml0eTEhMB8GCSqGSIb3DQEJARYSc3VwcG9y"+
        "dEBjYWNlcnQub3JnMB4XDTE5MTAwODE5Mjc1OFoXDTIwMDQwNTE5Mjc1OFowOjEY"+
        "MBYGA1UEAxMPQ0FjZXJ0IFdvVCBVc2VyMR4wHAYJKoZIhvcNAQkBFg9ldmg1MzEx"+
        "QHBzdS5lZHUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDyLadS1vLn"+
        "xXc1P0Ub7v/S3ww4AzfGTEIxvISUK2zdBxhrsLJO5b/8YSPDo5wCEf+7ZRoacWeW"+
        "a5pLRrUIxqJG/oKu3T1mOtbr6WFbMMBwt3z7yX87CkJrU0PolsDskX2JX/djXev/"+
        "yud6NIPc8AlmtLHzX99WcXKs7uq/ZzehGgFOp/V13o2IJy5E0X62PxMMzsm8VDjr"+
        "OLBUUTpxoX/7mqCu143n/z5kzBUW73pcsjrmhzr9lt8iY/W9eIS8JgHqZaSa/m0v"+
        "dxrjXPJ+2WPz4ZPSTpc2QqwkDDiJrlJqVIBmT35GrqOugcgnJ8prRPccfwSJebfY"+
        "hwVslul99BRnAgMBAAGjggE/MIIBOzAMBgNVHRMBAf8EAjAAMFYGCWCGSAGG+EIB"+
        "DQRJFkdUbyBnZXQgeW91ciBvd24gY2VydGlmaWNhdGUgZm9yIEZSRUUgaGVhZCBv"+
        "dmVyIHRvIGh0dHA6Ly93d3cuQ0FjZXJ0Lm9yZzAOBgNVHQ8BAf8EBAMCA6gwQAYD"+
        "VR0lBDkwNwYIKwYBBQUHAwQGCCsGAQUFBwMCBgorBgEEAYI3CgMEBgorBgEEAYI3"+
        "CgMDBglghkgBhvhCBAEwMgYIKwYBBQUHAQEEJjAkMCIGCCsGAQUFBzABhhZodHRw"+
        "Oi8vb2NzcC5jYWNlcnQub3JnMDEGA1UdHwQqMCgwJqAkoCKGIGh0dHA6Ly9jcmwu"+
        "Y2FjZXJ0Lm9yZy9yZXZva2UuY3JsMBoGA1UdEQQTMBGBD2V2aDUzMTFAcHN1LmVk"+
        "dTANBgkqhkiG9w0BAQsFAAOCAgEAvs6pFUmMP/bzkUvxmtXGC+E5t5tHQzy/jcDf"+
        "bYjOBV3ZmOLOIqi2Z1VVxlz5R+aOBlhi3Rav2crmFoMW2cjKR3MhC20RzJpKvbHS"+
        "MGb3mQ80LkVyrYDuM2sZ78SiJnui18irLrznqLH5cCUm8fSkyfYdiskUd1wgfbWC"+
        "cE1EZlIwQVw7qlSrIund+Y0DDBaZfn5FcHrVVxo2FRHnFBLlkQKZXNbTcX28kMdA"+
        "s+D8AvvhCsZuRvHDoIckwcmzgrNijsnmm9BFzCZzH6YkcrjTsqIJ2vNeYNSSo7hI"+
        "OrXZo5fwLrqXRlA5bb7ODI79L1CbeXoje+GkFjnxnmkNtHWYl+g9b0+tQ4dojXwS"+
        "zf9fjaTgZsiMSLz/YZR7ZgP298IJLitR68tAD56euVV0tCV2cm/tgwgRC0M+bdVH"+
        "66raHZwMXxJRqsInkGc02fggVTrtTBqRdjHmS0lvkh+ufBsFG3cQ9av2yPFslMRa"+
        "uNqpvKXl5ysVnpstrjEIdcSqNT9dWlExAueHy29t9CDXMz2hTpNFcL/HJTo/vClL"+
        "443SkjtLkYbxTrQ5Sax141BKBFxZNIBkjLbBo2awQWbwJDA061M4od5tFNCYjRSu"+
        "jIb7RCYcOCcICqC5E2b7GZlQ+uqLBSRkpyfTbaNt0MbK3y7QUpgI8+ymJa3JLZkp"+
        "/Vke/y8="+
        "-----END CERTIFICATE-----";

    public X509Certificate getX509cert() {
        return x509cert;
    }

    public String getName()
    { return name; }

    CACert(String name, String certInfo) throws CertificateException
    {
        this.name = name;
        certFactory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream bytes = new ByteArrayInputStream(certInfo.getBytes());
        x509cert = (X509Certificate)certFactory.generateCertificate(bytes);
    }
    
}

