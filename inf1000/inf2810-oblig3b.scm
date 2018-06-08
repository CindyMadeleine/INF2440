;;;; Oblig 3b
;;;;
;;;; Gruppemedlemmer:
;;;; Martin Mellum Jackson
;;;; Cindy Madeleine Svendsen Ceesay
;;;; Anders Orset


(load "evaluator.scm")


;;; Filens innhold:
;;;
;;; 1) Oppgavebesvarelser
;;; 2) Prosedyrer fra evaluator.scm som endres i mer enn én oppgave
;;; 3) Oppsett av evaluatoren
;;; 4) Tester til alle oppgavene


;;; NB! Hvis man skal legge inn egne tester til en oppgave er det enklest å
;;; legge dem inn i testseksjonen til oppgaven nederst i filen, i stedet for der
;;; oppgaven er besvart. Da er man sikker på at evaluatoren er satt opp med de
;;; riktige og nødvendige definisjonene for den oppgaven.
;;;
;;; (I DrRacket kan man bruke View -> Split (eller Ctrl+M) for å se på både
;;; besvarelsen og testene samtidig.)



;; Oppgave 1: Bli kjent med evaluatoren

;; 1a
;; ------------------------------------------------------------------------
;;
;; (foo 2 square):
;;
;; Når (foo 2 square) kalles, returneres 0. Den første "cond" vi støter på er
;; selve symbolet "cond", som sier til evaluatoren at her kommer et
;; cond-uttrykk. Denne "cond" blir derfor ikke evaluert videre.
;; 
;; Deretter vil evaluatoren evaluere uttrykket ((= cond 2) 0). Her må vi
;; evaluere "cond" for å finne dens verdi. Dette gjøres ved å finne denf
;; "innerste" bindingen av "cond" i de relevante rammene. Dette blir 2,
;; fordi det er verdien til argumentet i kallet (foo 2 square). Om "cond" ikke
;; hadde vært definert der, ville man gått ut til den globale omgivelsen og
;; funnet verdien 3 på grunn av kallet (define cond 3).
;;
;; Siden den første testen, dvs. (= cond 2), i cond-uttrykket er true fordi cond
;; er 2, evalueres hele uttrykket til verdien 0. 
;;
;; --------------------------------
;;
;; (foo 4 square):
;;
;; Når (foo 4 square) kalles, returneres 16. Den første delen av evalueringen er
;; lik som for (foo 2 square), bortsett fra at testen (= cond 2) ikke slår ut,
;; fordi cond nå er 4 i stedet for 2.
;;
;; Den første "else" blir brukt til å sjekke om cond-uttrykket har en else-gren,
;; og evalueres ikke til noe. Den andre "else" derimot, må evalueres som et
;; vanlig uttrykk. Her går man frem som når man skulle finne verdien av "cond" i
;; kallet for (foo 2 square). Den "innerste" bindingen av "else" er "square",
;; fordi "square" er argumentverdien til "else" i kallet (foo 4 square).
;;
;; Siden "square" tar kvadratroten av argumentet, og argumentet er 4, returneres
;; 16.
;;
;; --------------------------------
;;
;; (cond ((= cond 2) 0)
;;       (else (else 4))):
;;
;; Dette kallet evalueres til 2. Den første "cond" blir brukt til å avgjøre om
;; dette er et cond-uttrykk, og den evalueres derfor ikke videre. Den andre
;; "cond" evalueres til 3, fordi det er den eneste bindingen av "cond" som
;; eksisterer. Testen blir (= 3 2), som er false, og evaluatoren må evaluere
;; else-grenen.
;;
;; Den første "else" blir brukt til å avgjøre om dette er en else-gren, og blir
;; derfor ikke evaluert. Den andre "else" evalueres til prosedyren definert ved
;; (define (else x) (/ x 2)), fordi det er den eneste "else"-bindingen som
;; eksisterer. I dette tilfellet blir "x" satt til 4, og 4 delt på 2 er 2.



;;; Oppgave 2: Primitiver / innebygde prosedyrer

;; 2a
;; ------------------------------------------------------------------------

(set! primitive-procedures
      (append primitive-procedures
              (list (list '1+ (lambda (x) (+ x 1))))
              (list (list '1- (lambda (x) (- x 1))))))

;; (Må kalles før man setter opp den globale omgivelsen)


;; 2b
;; ------------------------------------------------------------------------

(define (install-primitive! name proc)
  (let ((global-frame (first-frame global)))
    (set-car! global-frame (cons name (car global-frame)))
    (set-cdr! global-frame (cons (list 'primitive proc) (cdr global-frame)))))



;;; Oppgave 3: Nye special forms og alternativ syntaks

;; 3a
;; ------------------------------------------------------------------------

;; Nye predikater
(define (and? exp) (tagged-list? exp 'and))
(define (or? exp) (tagged-list? exp 'or))

;; Ny selektor for å hente ut klausulene i et boolsk uttrykk (and / or)
(define (boolean-clauses exp) (cdr exp))

;; Nye prosedyrer for å evaluere and- og or-uttrykk. Her antar jeg at det
;; initielle uttrykket inneholder minst én klausul, det vil si at det som kommer
;; etter 'and eller 'or ikke er den tomme listen.
(define (eval-and clauses env)
  (let ((result (mc-eval (car clauses) env)))
    (cond ((null? (cdr clauses)) result)                   ;; Siste, returner
          ((false? result) result)                         ;; Første false, returner
          ((true? result) (eval-and (cdr clauses) env))))) ;; True, gå videre

(define (eval-or clauses env)
  (let ((result (mc-eval (car clauses) env)))
    (cond ((null? (cdr clauses)) result)                ;; Siste, returner
          ((false? result) (eval-or (cdr clauses) env)) ;; False, gå videre
          ((true? result) result))))                    ;; Første true, returner


;; 3b
;; ------------------------------------------------------------------------

;; Nye predikater
(define (then? exp) (tagged-list? exp 'then))
(define (elsif? exp) (tagged-list? exp 'elsif))
(define (else? exp) (tagged-list? exp 'else))

;; Nye selektorer
(define (if-rest exp) (cdr (cddddr exp))) ;; Rest kommer etter 'else / 'elsif
(define (else-consequent exp) (cadr (cddddr exp)))

;; Endringer av originalprosedyrene "if-consequent", "if-alternative" og "make-if"

(define (if-consequent exp)
  (if (then? (cddr exp))
      (cadddr exp)
      (error "IF format error:" exp)))

(define (if-alternative exp)
  (if (pair? (cddddr exp))
      (if (elsif? (cddddr exp))
          (cons 'if (if-rest exp)) ;; <- Erstatter første 'elsif med 'if, og
          (if (else? (cddddr exp))  ;;    så fortsetter rekursjonen
              (else-consequent exp)
              (error "IF format error:" exp)))
      (error "IF missing elsif/else:" exp)))

;; Må også oppdatere "make-if" for at if-uttrykk lagd av denne prosedyren skal
;; kunne evalueres (prosedyren "expand-clauses" i prekoden bruker "make-if"),
;; siden evaluatoren ikke skiller mellom if-uttrykk på den nye og gamle formen.
;;
;; Jeg har for enkelhets skyld ikke tatt med støtte for å legge til "elsif",
;; siden det heller ikke er nødvendig.
(define (make-if predicate consequent alternative)
  (list 'if predicate
        'then consequent
        'else alternative))


;; 3c
;; ------------------------------------------------------------------------

;; Nytt predikat
(define (let? exp) (tagged-list? exp 'let))

;; Nye selektorer (blir først prefikset med "old" for at det skal være lett å
;; bytte mellom gamle og nye let-uttrykk i evaluatoren).
(define (old-let-vars exp) (map car (cadr exp)))
(define (old-let-exps exp) (map cadr (cadr exp)))
(define (old-let-body exp) (caddr exp))

;; Setter evaluatoren til å bruke gamle let-uttrykk
(define let-vars old-let-vars)
(define let-exps old-let-exps)
(define let-body old-let-body)

;; Ny prosedyre som omformer et let-uttrykk til et lamba-uttrykk
;;
;; (Kroppen til let-uttrykket (dvs. (let-body exp)) må sendes inn som en liste
;; på grunn av hvordan prosedyren "make-lambda" i evaluator.scm fungerer.)
(define (let->lambda exp)
  (cons (make-lambda (let-vars exp) (list (let-body exp)))
        (let-exps exp)))


;; 3d
;; ------------------------------------------------------------------------

;; Nytt predikat
(define (let-last-pair? exp) (eq? 'in (cadddr exp)))

;; Nye selektorer
(define (let-first-var exp) (car exp))
(define (let-first-exp exp) (caddr exp))
(define (let-next-pair exp) (cddddr exp)) ;; Fortsett i listen av <var> <exp>-par

;; Nye definisjoner av selektorene fra oppgave 3c, som håndterer let-uttrykk på
;; den nye formen.

(define (new-let-body exp)
  (cond ((null? exp) (error "LET missing in:" exp))
        ((eq? (car exp) 'in) (if (pair? (cdr exp))
                                 (make-begin (cdr exp))
                                 (error "LET missing body:" exp)))
        (else (new-let-body (cdr exp)))))

(define (new-let-vars exp)
  (cond ((null? exp) (error "LET missing in:" exp))
        ((let-last-pair? exp) (list (let-first-var exp)))
        (else
         (if (eq? 'let (car exp)) ;; Første kall
             (cons (let-first-var (cdr exp)) ;; Eneste forskjell er (cdr exp)
                   (new-let-vars (let-next-pair (cdr exp))))
             (cons (let-first-var exp)
                   (new-let-vars (let-next-pair exp)))))))

(define (new-let-exps exp)
  (cond ((null? exp) (error "LET missing in:" exp))
        ((let-last-pair? exp) (list (let-first-exp exp)))
        (else
         (if (eq? 'let (car exp)) ;; Første kall
             (cons (let-first-exp (cdr exp)) ;; Eneste forskjell er (cdr exp)
                   (new-let-exps (let-next-pair (cdr exp))))
             (cons (let-first-exp exp)
                   (new-let-exps (let-next-pair exp)))))))

;; Setter evaluatoren til å bruke nye let-uttrykk
(define let-vars new-let-vars)
(define let-exps new-let-exps)
(define let-body new-let-body)


;; 3e
;; ------------------------------------------------------------------------
;;
;; Man bruker while ved å gjøre et prosedyrekall der "while" er operatoren,
;; første argument er betingelsen for at løkken skal fortsette, mens andre og
;; siste argument er while-kroppen. Eksempelkall/tester finnes nederst i filen.

;; Nytt predikat
(define (while? exp) (tagged-list? exp 'while))

;; Nye selektorer; et while-uttrykk har formen ('while <condition> <body>)
(define (while-condition exp) (cadr exp))
(define (while-body exp) (caddr exp))

;; Ny prosedyre som konverterer et while-uttrykk til if-uttrykk
(define (while->if exp)
  (make-if (while-condition exp)
           (make-begin (list (while-body exp) exp))))
           ; Trenger ikke alternativ til if-uttrykket pga. endringene nedenfor

;; NB! Før while-testene er det definert nye versjoner av "make-if" og "eval-if"
;; slik at man ikke trenger et alternativ i if-uttrykk. Ved å gjøre dette
;; slipper while-løkkene å returnere en symbolsk verdi når de terminerer.



;;; Prosedyrer fra prekoden som endres i flere oppgaver, sammen med kommentarer
;;; som markerer endringene og hvilken oppgave de tilhører
;;; -----------------------------------------------------------------------

(define (special-form? exp)
  (cond ((quoted? exp) #t)
        ((assignment? exp) #t)
        ((definition? exp) #t)
        ((if? exp) #t)
        ((lambda? exp) #t)
        ((begin? exp) #t)
        ((cond? exp) #t)
        ((and? exp) #t)   ;; 3a
        ((or? exp) #t)    ;; 3a
        ((let? exp) #t)   ;; 3c / 3d
        ((while? exp) #t) ;; 3e
        (else #f)))

(define (eval-special-form exp env)
  (cond ((quoted? exp) (text-of-quotation exp))
        ((assignment? exp) (eval-assignment exp env))
        ((definition? exp) (eval-definition exp env))
        ((if? exp) (eval-if exp env))
        ((lambda? exp)
         (make-procedure (lambda-parameters exp)
                         (lambda-body exp)
                         env))
        ((begin? exp) 
         (eval-sequence (begin-actions exp) env))
        ((cond? exp) (mc-eval (cond->if exp) env))
        ((and? exp) (eval-and (boolean-clauses exp) env)) ;; 3a
        ((or? exp) (eval-or (boolean-clauses exp) env))   ;; 3a
        ((let? exp) (mc-eval (let->lambda exp) env))      ;; 3c / 3d
        ((while? exp) (mc-eval (while->if exp) env))))    ;; 3e



;;; Oppsett av evaluatoren
;;; -----------------------------------------------------------------------

(set! the-global-environment (setup-environment))
(define global the-global-environment)



;;; Tester
;;; -----------------------------------------------------------------------

;; 2a
;; --------------------------------
(newline) (display "Tester 2a:") (newline)

(mc-eval '(1+ 2) global) ;; => 3
(mc-eval '(1- 2) global) ;; => 1


;; 2b
;; --------------------------------
(newline) (display "Tester 2b:") (newline)

(install-primitive! '3* (lambda (x) (* x 3)))

(mc-eval '(3* 7) global) ;; => 21
(mc-eval '(+ (3* -2) (3* 3)) global) ;; => 3


;; 3a
;; --------------------------------
(newline) (display "Tester 3a:") (newline)

(mc-eval '(and "abc" (+ 2 3) (= 1 2) "def") global) ;; => #f
(mc-eval '(and "abc" (+ 2 3) (= 1 1) "def") global) ;; => "def"
(mc-eval '(or #f (= 0 1) (- 7 2) "xyz") global) ;; => 5
(mc-eval '(or (and #t "foo") (and (/ 1 0) (exp 0 0))) global) ;; => foo


;; 3b
;; --------------------------------
(newline) (display "Tester 3b:") (newline)

(mc-eval '(if #t then "IF"
           elsif #t then "ELSIF 1"
           elsif #t then "ELSIF 2"
           else "ELSE") global) ;; => "IF"

(mc-eval '(if #f then "IF"
           elsif #t then "ELSIF 1"
           elsif #t then "ELSIF 2"
           else "ELSE") global) ;; => "ELSIF 1"

(mc-eval '(if #f then "IF"
           elsif #f then "ELSIF 1"
           elsif #t then "ELSIF 2"
           else "ELSE") global) ;; => "ELSIF 2"

(mc-eval '(if #f then "IF"
           elsif #f then "ELSIF 1"
           elsif #f then "ELSIF 2"
           else "ELSE") global) ;; => "ELSE"


;; 3c
;; --------------------------------
(newline) (display "Tester 3c:") (newline)

;; Bruker de "gamle" prosedyrene for at evaluatoren skal kunne tolke let-uttrykk
;; på den gamle formen
(define let-vars old-let-vars)
(define let-exps old-let-exps)
(define let-body old-let-body)

(mc-eval '(let ((a 1) (b 2) (c 3)) (+ a b c)) global) ;; => 6
(mc-eval '(let ((a 'foo) (b 'bar)) (cons a b)) global) ;; => (foo . bar)


;; 3d
;; --------------------------------
(newline) (display "Tester 3d:") (newline)

;; Bruker de "nye" prosedyrene for at evaluatoren skal kunne tolke let-uttrykk
;; på den nye formen
(define let-vars new-let-vars)
(define let-exps new-let-exps)
(define let-body new-let-body)

(mc-eval '(let x = 2 and
               y = 3 in
            (display (cons x y))
            (+ x y))
         global) ;; => (2 . 3)5

(mc-eval '(let a = 1 and
               b = 2 and
               c = 3 in
            (+ a b c))
         global) ;; => 6


;; 3e
;; --------------------------------
;;
;; Måten man bruker while på er å kalle while som en prosedyre, med betingelsen
;; som første argument, og while-kroppen som andre argument:
;;
;; (while <condition> <body>)
;;
;; Først kommer nye definisjoner av "make-if" og "eval-if" for å tillate at man
;; dropper alternativet i if-uttrykk. I tillegg blir prosedyrene
;; "if-alternative" og "if-consequent" satt tilbake til sin opprinnelige
;; definisjon.

(newline) (display "Tester 3e:") (newline)

;; Ny "make-if" som tillater at man dropper alternativ/else-delen
;; (Dette gjør at while-løkken kan avslutte uten å returnere en symbolsk verdi)
(define (make-if predicate consequent . alternative)
  (append (list 'if predicate consequent)
          (if (null? alternative)
              '()
              alternative)))

;; Må også oppdatere "eval-if" for å tillate at man ikke har alternativ/else-delen
(define (eval-if exp env)
  (if (true? (mc-eval (if-predicate exp) env))
      (mc-eval (if-consequent exp) env)
      (if (not (eq? 'false (if-alternative exp)))
          (mc-eval (if-alternative exp) env))))

(define (if-consequent exp) (caddr exp))

(define (if-alternative exp)
  (if (not (null? (cdddr exp)))
      (cadddr exp)
      'false))


;; Test 1/3
(newline) (display "3e 1/3:") (newline)

; While-uttrykk som viser lst, før lst settes til (cdr lst)
(define test1
  '(while (not (null? lst))
          (begin
            (display lst)
            (newline)
            (set! lst (cdr lst)))))

(mc-eval '(define lst '(1 2 3 4 5)) global)
; => ok

(mc-eval test1 global)
; => (1 2 3 4 5)
;    (2 3 4 5)
;    (3 4 5)
;    (4 5)
;    (5)


;; Test 2/3
(newline) (display "3e 2/3:") (newline)

; While-uttrykk som viser n og legger til 1, helt til n er 3
(define test2
  '(while (not (= n 3))
          (begin
            (display n) (newline)
            (set! n (+ n 1)))))

(mc-eval '(define n -2) global)
; => ok

(mc-eval test2 global)
; => -2
;     1
;     0
;     1
;     2


;; Test 3/3
(newline) (display "3e 3/3:") (newline)

; While-uttrykk som aldri kjører kroppen sin
(define test3
  '(while #f (/ 0 0)))

(mc-eval test3 global)
; => (tomt resultat)