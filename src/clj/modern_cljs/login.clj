(ns modern-cljs.login
  (:require [bouncer.core :refer [validate]]
            [bouncer.validators :refer [defvalidator]]))

(def ^:dynamic *re-email*
  #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(def ^:dynamic *re-password*
  #"^(?=.*\d).{4,8}$")

(defvalidator email-validator
  [email]
  (validate {:email email} 
              :email (v/matches *re-email* :message "Invalid email format")))

(defvalidator password-validator
  [password]
  (validate {:password password} 
              :password (v/matches *re-password* :message "Invalid password format")))

(defvalidator email-password-validator
  [email password]
  (validate {:email email :password password} 
              :email (v/matches *re-email* :message "Invalid email format")
              :password (v/matches *re-password* :message "Invalid password format")))

(declare validate-email validate-password)

(defn authenticate-user [email password]
  (if (or (empty? email) (empty? password))
    (str "Please complete the form")
    (if (nil? (first (email-password-validator email password)))
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