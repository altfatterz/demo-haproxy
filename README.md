
The backend is simple Spring Boot app exposing the request headers on `http://localhost:8080`
Start up the backend and verify that the following is working:
```
http://localhost:8080
```

Now we would like to access this service through https via HAProxy which terminates the SSL connection calculates
the client certificate fingerprint and passes it through the backend in a header `x-ssl-client-sha1`


Server certificate:

```
openssl genrsa -out haproxy.key 2048
openssl req -new -key haproxy.key -out haproxy.csr -subj '/C=NL/ST=Amsterdam/L=Amsterdam/O=GeriMedica/OU=R&D/CN=haproxy.gerimedica.nl'
openssl req -in haproxy.csr -noout -text
openssl x509 -req -days 365 -in haproxy.csr -signkey haproxy.key -out haproxy.crt
cat haproxy.crt haproxy.key | tee haproxy.pem
```

Client certificate:

Create self signed certificate:
```
openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout client.key -out client.crt -subj '/C=NL/ST=Amsterdam/L=Amsterdam/O=TTS/OU=R&D/CN=www.tts.nl'
// client.key and client.crt are created
openssl x509 -in client.crt -text -noout
```



Start up the haproxy in the `haproxy` folder

```
haproxy -f haproxy.cfg
```

Verify that you can access the backend through HAProxy via http:

```
http://localhost:8081
```

Verify that you can access the backend through HAProxy via http:

```
https://localhost:8443/

```


My problem is that the `x-ssl-client-sha1` is set to empty string while running:

```
$ http --verify=no --cert client.pem --cert-key=client.key https://localhost:8443

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Date: Fri, 24 Jun 2016 12:36:45 GMT
Server: Apache-Coyote/1.1
Transfer-Encoding: chunked
X-Application-Context: application

{
    "accept": "*/*",
    "accept-encoding": "gzip, deflate",
    "connection": "close",
    "host": "localhost:8443",
    "user-agent": "HTTPie/0.9.3",
    "x-forwarded-for": "127.0.0.1",
    "x-forwarded-port": "8443",
    "x-forwarded-proto": "https",
    "x-ssl-client-sha1": ""
}
```

What am I doing wrong?
