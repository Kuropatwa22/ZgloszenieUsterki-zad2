# Zadanie 2 — Zgłoszenie usterki

Zrobiłem formularz do zgłaszania usterek w Android Studio.

W formularzu można wpisać tytuł, lokalizację i opis usterki, wybrać kategorię oraz priorytet.

Dodałem też zgodę na kontakt i pole e-mail.

## Użyte kontrolki

- EditText — do wpisywania danych.
- Spinner — do wyboru kategorii.
- RadioGroup i RadioButton — do wyboru priorytetu.
- CheckBox — do wyrażenia zgody na kontakt.
- Button — do wysłania zgłoszenia.

Cały formularz jest w ScrollView, więc można go przewijać.

## Walidacja

Program sprawdza, czy wpisano tytuł, wybrano kategorię i priorytet, podano lokalizację i opis.

Jeśli użytkownik zgodzi się na kontakt, musi również wpisać poprawny adres e-mail.

Przy błędach w polach tekstowych użyłem setError. Program zatrzymuje sprawdzanie na pierwszym błędzie.

## Zależność między polami

Po zaznaczeniu checkboxa „Zgadzam się na kontakt” odblokowuje się pole e-mail.

Po odznaczeniu checkboxa pole zostaje zablokowane i wyczyszczone.

## Potwierdzenie i cofnięcie

Po poprawnym wypełnieniu formularza pojawia się AlertDialog z pytaniem o potwierdzenie.

Po potwierdzeniu wyświetla się Snackbar z przyciskiem Cofnij.

Aplikacja symuluje wysłanie zgłoszenia, ale nie zapisuje danych na serwerze.

## Który interfejs nasłuchiwania ma więcej niż jedną metodę?

Na przykład Spinner używa OnItemSelectedListener, który ma dwie metody: onItemSelected i onNothingSelected.

Dlatego nie można go zapisać jako zwykłej lambdy i trzeba użyć klasy anonimowej.

CheckBox używa OnCheckedChangeListener, który ma jedną metodę, dlatego można użyć lambdy.

W moim formularzu użyłem lambdy przy CheckBox, a Spinner podłączyłem przez ArrayAdapter.
