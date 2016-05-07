(ns clj-pat.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:use [slingshot.slingshot :only [try+]]))

(defn get-body [response]
  (json/parse-string (:body response) true))

(defn get-data [url]
  (try+
    (let [response (client/get url {:throw-entire-message? true})]
      (get-body response))
    (catch Object _
      (let [body (get-body (:object &throw-context))]
        {:error {:status (:code body) :message (:error body)}}))))

(def base-url "http://uk-postcodes.com/")

(defn postcode-url [postcode]
  (let [code (clojure.string/replace postcode " " "")]
    (str base-url "postcode/" code ".json")))

(defn postcode [code]
  (get-data (postcode-url code)))
