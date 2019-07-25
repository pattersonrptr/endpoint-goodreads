(ns book-api-clojure.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [clj-http.client :as client]))

(defn home-page
  [request]
  (ring-resp/response "<h1>API GoodReads</h1>"))

(defn book-detail
   [request]

   (def url "https://www.goodreads.com/search.xml?key=jpnv6y27l2H7KwlmoMqkUg&q=")

   (try
       (let [title (get-in request [:query-params :title])]
           (clj-http.client/get 
				           (str url title)))
  					(catch Exception e
  					    (prn "Erro" e))))
   				

;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])
(def common-interceptors-json [(body-params/body-params) http/json-body])

;; Tabular routes
(def routes #{["/" :get (conj common-interceptors `home-page)]
              ["/book-detail" :get (conj common-interceptors-json `book-detail)]})

;; Consumed by book-api-clojure.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod

              ::http/routes routes


              ::http/resource-path "/public"

              ::http/type :jetty
           
              ::http/port 8080
       
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false
                                       }})
