```

openssl genrsa -out haproxy.key 1024
openssl req -new -key haproxy.key -out haproxy.csr
openssl x509 -req -days 365 -in haproxy.csr -signkey haproxy.key -out haproxy.crt

cat haproxy.crt haproxy.key | tee haproxy.pem

haproxy -f haproxy.cfg
```


The running backend service:

```
http://localhost:8080
```

Access with HAProxy with http

http://localhost:8081

Access with HAProxy with https:

```
https://localhost:8443/

```

```
curl --insecure https://localhost:8443
```