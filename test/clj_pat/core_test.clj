(ns clj-pat.core-test
  (:require [clojure.test :refer :all]
            [clj-pat.core :refer :all]
            [clj-http.client :as client]
            [cheshire.core :as json])
  (:use clj-http.fake))

(deftest test-postcode
  (let [fake-body (json/generate-string {:postcode "KM21 4JR" :city "test City"})]
    (with-fake-routes {"http://uk-postcodes.com/postcode/KM214JR.json" (fn [request] {:status 200 :headers {} :body fake-body})}
      (is (= { "postcode" "KM21 4JR" "city" "test City"} (postcode "KM21 4JR"))))))
