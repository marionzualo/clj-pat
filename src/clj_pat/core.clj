(ns clj-pat.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn postcode
  [code]
  (let [parsed-code (clojure.string/replace code " " "")
        url (str "http://uk-postcodes.com/postcode/" parsed-code ".json")
        response (client/get url)
        data (json/parse-string (:body response))] data))
