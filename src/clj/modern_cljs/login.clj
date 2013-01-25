(ns modern-cljs.login
  (:require [valip.core :refer [validate ]]
            [valip.predicates :refer [matches]]))

(def ^:dynamic *re-email*
  #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(def ^:dynamic *re-password*
  #"^(?=.*\d).{4,8}$")

(defn email-validator
  [email]
  (let [user-email {:email email}]
    (validate user-email
                 [:email (matches *re-email*) "Invalid email format"])))

(defn password-validator
  [password]
  (let [user-password {:password password}]
    (validate user-password
                 [:password (matches *re-password*) "Invalid password format"])))

(defn email-password-validation
  [email password]
  (let [user {:email email :password password}]
    (validate user
                 [:email (matches *re-email*) "Invalid email format"]
                 [:password (matches *re-password*) "Invalid password format"])))
;; (declare validate-email validate-password)
(defn authenticate-user [email password]
  (if (or (empty? email) (empty? password))
    (str "Please complete the form")
    (if (nil? (email-password-validation email password))
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



