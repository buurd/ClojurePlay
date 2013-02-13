(ns hello-world.handler
  (:use [compojure.core]
        [hiccup.core]
        [hiccup.page]
        [hiccup.form]
        [hiccup.element]
        [hiccup.def])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
      ))

(defn parse-int [s]
   (Integer. 
           (re-find  #"\d+" s )))

(defn header [title]
  (html 
   [:head
    [:title title]
    (include-js "/cljs/main.js")
    (include-js "/cljs/play.js")
    ]))

(defn add-form [x y]
  (form-to {:id "form"} 
   [:post "/calc"]
        [:div
         [:ul
          [:li "Ett "
           [:input {:type "text"
           :name  "add1"
           :id "add1"
           :value x}]]
          [:li "Två "
           [:input {:type "text"
           :name "add2"
           :id "add2"
           :value y}]]]
         [:input {:type "button"
                :value "Summera"}]]))

(defn start-page []
   (html5
       (header "Playing with Clojure ... ")
       [:body
        [:div
         [:h2 "Start page"]]
        (link-to "/calc" "Add some... ")
        ]))

(defn add-page [params] 
   (html5
       (header "Playing with Clojure... ")
       [:body
        [:div
         [:h2 "Adding"]]
        [:div (+ (parse-int(params :add1)) (parse-int(params :add2)))]
        (add-form (params :add1) (params :add2))
        ]))

(defroutes app-routes
  (GET "/" [] (start-page))
  (GET "/calc" [] (add-page {:add1 "0" :add2 "0"}))
  (POST "/calc" {params :params} (add-page params))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
