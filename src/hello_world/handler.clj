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
   (Integer. (re-find  #"\d+" s )))

(defn add-form [x y]
  (form-to [:post "/calc"]
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
         [:input {:type "submit"
                :value "Summera"}]]))



(defn start-page []
  (html 
   (html5
      [:html
       [:head
        [:title "Playing with Clojure"]]
       [:body
        [:div
         [:h2 "Start page"]]
        (link-to "/calc" "Add some... ")
        ]])))

(defn add-page [params] 
  (html 
   (html5
      [:html
       [:head
        [:title "Playing with Clojure"]]
       [:body
        [:div
         [:h2 "Adding"]]
        [:div (+ (parse-int(params :add1)) (parse-int(params :add2)))]
        (add-form (params :add1) (params :add2))
        ]])))

(defroutes app-routes
  (GET "/" [] (start-page))
  (GET "/calc" [] (add-page {:add1 "0" :add2 "0"}))
  (POST "/calc" {params :params} (add-page params))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
