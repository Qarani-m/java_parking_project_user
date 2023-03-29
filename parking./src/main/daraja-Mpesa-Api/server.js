
const express = require("express");
const app = express();
const http = require("http");
const bodyParser = require("body-parser");
const request = require("request");
const moment = require("moment");

const port = 6067;
const hostname = "localhost";
app.use(bodyParser.json());
const server = http.createServer(app);

app.get("/", (req, res) => {
    res.send("MPESA DARAJA implementation  WITH NODE JS BY Group 3");
    var timeStamp = moment().format("YYYYMMDDHHmmss");
    console.log(timeStamp);
});

//ACCESS TOKEN ROUTE
app.get("/access_token", (req, res) => {
    getAccessToken()
        .then((accessToken) => {
            res.send("😀 Your access token is " + accessToken);
        })
        .catch(console.log);
});
//MPESA STK PUSH ROUTE
app.get("/stkpush", (req, res) => {
    console.log(req.query.phone)
    getAccessToken()
        .then((accessToken) => {
            const url =
                "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest",
                auth = "Bearer " + accessToken;
            var timestamp = moment().format("YYYYMMDDHHmmss");
            const password = new Buffer.from(
                "174379" +
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919" +
                timestamp
            ).toString("base64");

            request(
                {
                    url: url,
                    method: "POST",
                    headers: {
                        Authorization: auth,
                    },
                    json: {
                        BusinessShortCode: "174379",
                        Password: password,
                        Timestamp: timestamp,
                        TransactionType: "CustomerPayBillOnline",
                        Amount: "1",
                        PartyA: "254768168060",
                        PartyB: "174379",
                        PhoneNumber: req.query.phone,
                        // CallBackURL: "https://6859-102-213-241-18.in.ngrok.io/callback/",
                        CallBackURL: "https://umeskiasoftwares.com/umswifi/callback",
                        AccountReference: "Qarani",
                        TransactionDesc: "Mpesa Daraja API stk push test",
                    },
                },
                function (error, response, body) {

                    if (error) {
                        console.log(error);
                    } else {
                        res.send(body)
                        console.log(body)
                    }
                }
            );

        })
        .catch(console.log);
});
// ACCESS TOKEN FUNCTION
function getAccessToken() {
    const consumer_key = "uy5v4TX4miqTjOIA6EJKvfuuXU01yzbR";
    const consumer_secret = "LAVzZcQhRsG6LsRh";
    const url =
        "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
    const auth =
        "Basic " +
        new Buffer.from(consumer_key + ":" + consumer_secret).toString("base64");
    return new Promise((response, reject) => {
        request(
            {
                url: url,
                headers: {
                    Authorization: auth,
                },
            },
            function (error, res, body) {
                try {
                    var jsonBody = JSON.parse(body);
                    console.log(body);
                } catch (err) {
                    console.log(err);
                }

                if (error) {
                    console.log(error);
                } else {
                    const accessToken = jsonBody.access_token;
                    response(accessToken);
                }
            }
        );
    });
}


server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});



