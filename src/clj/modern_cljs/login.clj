(ns modern-cljs.login
  (:require [metis.core :refer [defvalidator]]))

(def ^:dynamic *re-email*
  #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(def ^:dynamic *re-password*
  #"^(?=.*\d).{4,8}$")

(defvalidator email-validator
  [:email :formatted {:pattern *re-email* :message "Incorrect email format"}])

(defvalidator password-validator
  [:password :formatted {:pattern *re-password* :message "Incorrect password format"}])

(defvalidator email-password-validator
  [:email :formatted {:pattern *re-email* :message "Incorrect email format"}]
  [:password :formatted {:pattern *re-password* :message "Incorrect password format"}])

(declare validate-email validate-password)

(defn authenticate-user [email password]
  (if (or (empty? email) (empty? password))
    (str "Please complete the form")
    (if (empty? (email-password-validator {:email email :password password}))
      (str email " and " password
           " passed the formal validation, but you still have to be authenticated"))))

(comment
(defn validate-email [email]
  (if  (re-matches *re-email* email)
    true))

(defn validate-password [password]
  (if  (re-matches *re-password* password)
    true))

)
