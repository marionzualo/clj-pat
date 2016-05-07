# clj-pat

clj-pat is a small library for getting information about, postcodes in the UK. At its core, the postcode data is
provided by the [Ordnance Survey OS OpenData](http://www.ordnancesurvey.co.uk/oswebsite/products/os-opendata.html)
initiative, but this library is actually a thin wrapper for a [web-service](http://www.uk-postcodes.com/) provided by
[Stuart Harrison](http://twitter.com/pezholio).

This was inspired by the [Python Client](https://postcodes.readthedocs.org/en/latest/) by
[Edd Robinson](http://about.me/eddrobinson).

## Usage

Return data for a postcode
```clojure
>>>> (clj-pat.core/postcode "IP12 4JR")
{"postcode" "IP12 4JR", "geo" {"lat" 52.096880109249916, "lng" 1.3037915126824386, "easting" 626389.0, "northing" 249432.0, "geohash" "http://geohash.org/u12bmevqkd36"}, "administrative" {"council" {"title" "Suffolk Coastal", "uri" "http://statistics.data.gov.uk/id/statistical-geography/E07000205", "code" "E07000205"}, "county" {"title" "Suffolk", "uri" "http://statistics.data.gov.uk/id/statistical-geography/E10000029", "code" "E10000029"}, "ward" {"title" "Farlingaye", "uri" "http://statistics.data.gov.uk/id/statistical-geography/E05007196", "code" "E05007196"}, "constituency" {"title" "Suffolk Coastal", "uri" "http://statistics.data.gov.uk/id/statistical-geography/E14000981", "code" "E14000981"}, "parish" {"title" "Woodbridge", "uri" "http://statistics.data.gov.uk/id/statistical-geography/E04009480", "code" "E04009480"}, "electoral_district" {"title" "Woodbridge", "uri" "http://data.ordnancesurvey.co.uk/id/7000000000015224", "code" "7000000000015224"}}}
```

Return data for the nearest postcode to a point
```clojure
>>> (clj-pat.core/get-nearest-postcode 51.5054914 -0.0929278)
{:postcode "SE1 9HR", :geo {:lat 51.50511048146166, :lng -0.09288950719200631, :easting 532457.0, :northing 180188.0, :geohash "http://geohash.org/gcpvjbhr582x"}, :administrative {:council {:title "Southwark", :uri "http://statistics.data.gov.uk/id/statistical-geography/E09000028", :code "E09000028"}, :ward {:title "Cathedrals", :uri "http://statistics.data.gov.uk/id/statistical-geography/E05000536", :code "E05000536"}, :constituency {:title "Bermondsey and Old Southwark", :uri "http://statistics.data.gov.uk/id/statistical-geography/E14000553", :code "E14000553"}}}
```

## License

Copyright © 2016 Mário Nzualo

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
