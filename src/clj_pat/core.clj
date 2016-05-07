(ns clj-pat.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:use [slingshot.slingshot :only [try+]]))

(defn get-body [response]
  (json/parse-string (:body response) true))

(defn get-data [url & {:keys [params] :or {:params {}}}]
  (try+
    (let [response (client/get url {:throw-entire-message? true :query-params params})]
      (get-body response))
    (catch Object _
      (let [body (get-body (:object &throw-context))]
        {:error {:status (:code body) :message (:error body)}}))))

(def base-url "http://uk-postcodes.com/")

(defn sanitize [postcode] (clojure.string/replace postcode " " ""))

(defn postcode-url [postcode]
  (str base-url "postcode/" (sanitize postcode) ".json"))

(defn nearest-postcode-url [lat lng]
  (str base-url "latlng/" lat "," lng ".json"))

(def postcodes-within-distance-url (str base-url "postcode/nearest"))
;; Public API

; Return data for a postcode
(defn get-postcode [postcode]
  (get-data (postcode-url postcode)))

; Return data for the nearest postcode to a point
(defn get-nearest-postcode [lat lng]
  (get-data (nearest-postcode-url lat lng)))

; Return data for postcodes within x distance (miles) of a postcode
(defn get-postcodes-within-distance
  ([miles postcode]
   (get-data postcodes-within-distance-url :params {:miles miles :postcode (sanitize postcode) :format "json"}))
  ([miles lat lng]
   (get-data postcodes-within-distance-url :params {:miles miles :lat lat :lng lng :format "json"})))
