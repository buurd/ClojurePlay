(ns hello)
(defn ^:export greet []
  (js/alert "hej hopp"))

(defn init []
  (if (and js/document
           (.-getElementById js/document))
      
    (let [login-form (.getElementById js/document "form")]
      (set! (.-onclick login-form) greet))))

(set! (.-onload js/window) init)