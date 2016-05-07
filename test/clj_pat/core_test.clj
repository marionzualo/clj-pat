(ns clj-pat.core-test
  (:require [clojure.test :refer :all]
            [clj-pat.core :refer :all]
            [clj-http.client :as client]
            [cheshire.core :as json])
  (:use clj-http.fake))

(deftest test-postcode
  (let [fake-body (json/generate-string {:postcode "KM21 4JR" :city "test City"})]
    (with-fake-routes {"http://uk-postcodes.com/postcode/KM214JR.json" (fn [request] {:status 200 :headers {} :body fake-body})}
      (is (= {:postcode "KM21 4JR" :city "test City"} (get-postcode "KM21 4JR"))))))

(deftest test-postcode-error
  (let [fake-body (json/generate-string {:code 404 :error "Postcode is not valid"})]
    (with-fake-routes {"http://uk-postcodes.com/postcode/KM214JR.json" (fn [request] {:status 404 :headers {} :body fake-body})}
      (is (= {:error {:status 404 :message "Postcode is not valid"} } (get-postcode "KM21 4JR"))))))

(deftest test-nearest-postcode
  (let [fake-body (json/generate-string {:postcode "KM21 4JR" :geo {:lat 51 :lng 49}})]
    (with-fake-routes {"http://uk-postcodes.com/latlng/51,49.json" (fn [request] {:status 200 :headers {} :body fake-body})}
      (is (= {:postcode "KM21 4JR" :geo {:lat 51 :lng 49}} (get-nearest-postcode 51 49))))))

(deftest test-nearest-postcode-error
  (let [fake-body (json/generate-string {:code 404 :error "Postcode not found"})]
    (with-fake-routes {"http://uk-postcodes.com/latlng/51,49.json" (fn [request] {:status 404 :headers {} :body fake-body})}
      (is (= {:error {:status 404 :message "Postcode not found"} } (get-nearest-postcode 51 49))))))

(deftest test-postcodes-within-distance-of-postcode
  (let [fake-body (json/generate-string [{:postcode "KM21 4JR"} {:postcode "ABS12 11"}])]
    (with-fake-routes { {:address "http://uk-postcodes.com/postcode/nearest"
                        :query-params {:miles 1 :postcode "LOP32" :format "json"}} (fn [request] {:status 200 :headers {} :body fake-body})}
      (is (= [{:postcode "KM21 4JR"} {:postcode "ABS12 11"}] (get-postcodes-within-distance 1 "LOP 32"))))))

(deftest test-postcodes-within-distance-of-geo
  (let [fake-body (json/generate-string [{:postcode "KM21 4JR"} {:postcode "ABS12 11"}])]
    (with-fake-routes { {:address "http://uk-postcodes.com/postcode/nearest"
                        :query-params {:miles 1 :lat 51 :lng 49 :format "json"}} (fn [request] {:status 200 :headers {} :body fake-body})}
      (is (= [{:postcode "KM21 4JR"} {:postcode "ABS12 11"}] (get-postcodes-within-distance 1 51 49))))))
