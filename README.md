**1. Instalacja i uruchomienie** <br>
Program jest napisany w SpringBoot, najprościej uruchomić go  z intellij

**2. Struktura** <br>
Zrobiłem to tak, żeby komponenty były od siebie maksymalnie niezależne i wymienialne. Sorki jeśli jest trochę przekombinowane,
jak macie jakieś sugestie lub coś jest ewidentnie słabe, to piszcie, możemy poprawić. Wydaje mi się jednak, że z takim
szkieletem nic nas już nie zaskoczy.
Pakiety:
error - wrzuciłem tam parę ogólnych wyjątków
model - definicje modeli do faktycznej logiki problemu
repository - program działa tak, że po zadeklarowaniu problemu można go rozwiązywać różnymi metodami. W tym czasie
musi być gdzieś zapiany. Zrobiłem takie pseudo repozytorium, które po prostu trzyma referencję
serivces - wymienialne ( w większości ) komponenty programu, w tym:
  costcalculator - czyli funkcja celu. Wymienialna, możliwa do zadeklarowania przy tworzeniu nowego
  problemu. Rodzaje są trzymane w enumie CostCalculationType, a implementacja CostCalculator powinny
  być oznaczane adnotację ForCostCalculationType, żeby Spring to znalazł
  generator - losowo generuje problem według dostarczonej konfiguracji
  reader - parser poleceń z wejścia
  solver - rozwiązania (obecnie losowe, pewnie będziemy wrzucać docelowo pszczele). W komendzie 
  można wybrać rozwiązanie, które chcemy użyć. Tworząc nowe rozwiązanie trzeba dodać kolejną wartość
  do enuma SolveType i zaimplementować ProblemSolver i SolverParamsResolver, oba oznaczając adnotacją
  ForSolveType. 
 
**3. Obsługa** <br>
Po uruchomieniu program generuje domyślny problem (domyślne cechy wrzuciłem do NewCommand bezpośrednio)

komendy:

new <parametry które możecie podejreć w CommandParser> : tworzy nowy problem
przykład: <br>
new chance_for_all 12 3 7 50 40 <br>

get : wywietla obecny problem
przykład: <br>
get 

solve <parametry które możecie podejreć w CommandParser> : rozwiązuje problem i wyświetla
przykład: <br>
solve random

end: kończy program
przykład: <br>
end

UWAGA: ostrożnie z ustawianiem dla problemu wysokiej sumy statystyk, bo obecnie jest kretyńska implementacja
losowania, która leci pętlą od 0 do tej sumy dla każdej jednostki :D

**4. Testy** <br>
Dodałem do zależności spocka, bo testy imo się tam szybciej piszę. 