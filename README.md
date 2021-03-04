# bidding-system

Run `sbt run` to run the application.

Run `sbt test` to run project unit tests

Request example:
`curl --location --request POST 'http://localhost:8080/bids' \
 --header 'Content-Type: application/json' \
 --data-raw '{
   "id": "SGu1Jpq1IO",
   "site": {
     "id": "0006a522ce0f4bbbbaa6b3c38cafaa0f",
     "domain": "fake.tld"
   },
   "device": {
     "id": "440579f4b408831516ebd02f6e1c31b4",
     "geo": {
       "country": "LT"
     }
   },
   "imp": [
     {
     "id": "1",
     "wmin": 50,
     "wmax": 300,
     "hmin": 100,
     "hmax": 300,
     "h": 250,
     "w": 300,
     "bidFloor": 3.12123
     }
   ],
   "user": {
     "geo": {
       "country": "LT"
     },
     "id": "USARIO1"
   }
 }'` 
