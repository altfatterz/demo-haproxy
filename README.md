
The backend is simple Spring Boot app exposing the request headers on `http://localhost:8080`
Start up the backend and verify that the following is working:
```
http://localhost:8080
```

Now we would like to access this service through https via HAProxy which terminates the SSL connection calculates
the client certificate fingerprint and passes it through the backend in a header `x-ssl-client-sha1`

The `server.key`, `server.csr`, `server.crt` and `server.pem` where created following:

```
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
cat server.crt server.key | tee server.pem
```

Start up the haproxy in the `haproxy` folder

```
haproxy -f haproxy.cfg
```

Verify that you can access the backend through HAProxy via http:

http://localhost:8081

Verify that you can access the backend through HAProxy via http:

```
https://localhost:8443/

```

The SSL client certificate was created certificate with the following commands:

```
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -days 365 -in client.csr -CA server.crt -CAkey server.key -set_serial 01 -out client.crt
openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12
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
