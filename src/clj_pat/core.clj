(ns clj-pat.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:use [slingshot.slingshot :only [try+]]))

(defn get-body [response]
  (json/parse-string (:body response)))

(defn get-data [url]
  (try+
    (let [response (client/get url {:throw-entire-message? true})]
      (get-body response))
    (catch Object _
      (let [body (get-body (:object &throw-context))]
        {:error {:status (get body "code") :message (get body "error")}}))))

(defn postcode [code]
  (let [parsed-code (clojure.string/replace code " " "")
        url (str "http://uk-postcodes.com/postcode/" parsed-code ".json")]
    (get-data url)))
