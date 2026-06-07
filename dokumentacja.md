# 🖼️ Gallery Editor Android (Kotlin + Jetpack Compose + uCrop)

Nowoczesna aplikacja do edycji zdjęć na Androida, napisana w **Kotlin + Jetpack Compose**. Umożliwia wybór zdjęć z galerii, kadrowanie, obracanie, odbijanie, regulację jasności, kontrastu i nasycenia oraz zapis gotowego obrazu do galerii urządzenia.

---

## 🚀 Funkcje aplikacji

✔ wybór wielu zdjęć z galerii

✔ podgląd miniatur wybranych zdjęć

✔ kadrowanie zdjęć przy użyciu **uCrop**

✔ regulacja jasności (Brightness)

✔ regulacja kontrastu (Contrast)

✔ regulacja nasycenia kolorów (Saturation)

✔ obrót zdjęcia o ±90°

✔ odbicie poziome (Flip Horizontal)

✔ odbicie pionowe (Flip Vertical)

✔ zapis edytowanego zdjęcia do galerii

✔ nowoczesny interfejs oparty o Jetpack Compose

---

## 📦 Wymagania

* Android Studio
* Android SDK 24+
* Kotlin 2.0+
* Jetpack Compose
* Gradle
* uCrop
* Coil

---

## ▶️ Instalacja i uruchomienie

### 1. Sklonuj repozytorium

```bash
git clone https://github.com/TWOJ_LOGIN/Gallery-Android.git
```

### 2. Przejdź do folderu projektu

```bash
cd Gallery-Android
```

### 3. Otwórz projekt w Android Studio

```text
File → Open
```

Wybierz folder projektu.

### 4. Synchronizacja Gradle

Android Studio automatycznie pobierze wszystkie zależności.

Jeżeli będzie to wymagane:

```text
File → Sync Project with Gradle Files
```

### 5. Uruchom aplikację

Kliknij:

```text
Run ▶ app
```

lub użyj skrótu:

```text
Shift + F10
```

---

## ✨ Funkcje edycji zdjęć

### 🌞 Jasność (Brightness)

Pozwala rozjaśnić lub przyciemnić zdjęcie.

Zakres:

```text
-100% → +100%
```

---

### 🎨 Kontrast (Contrast)

Zmienia różnicę pomiędzy jasnymi i ciemnymi obszarami obrazu.

Zakres:

```text
0 → 2
```

---

### 🌈 Nasycenie (Saturation)

Reguluje intensywność kolorów.

Zakres:

```text
0 → 2
```

* 0 = zdjęcie czarno-białe
* 1 = wartość domyślna
* 2 = maksymalne nasycenie

---

### 🔄 Obracanie

Przyciski:

```text
Rotate -90°
Rotate +90°
```

umożliwiają szybki obrót zdjęcia.

---

### ↔ Odbicie poziome

Przycisk:

```text
Flip H
```

odwraca zdjęcie w poziomie.

---

### ↕ Odbicie pionowe

Przycisk:

```text
Flip V
```

odwraca zdjęcie w pionie.

---

### ✂ Kadrowanie (Crop)

Kadrowanie realizowane jest przy pomocy biblioteki **uCrop**.

Funkcje:

* dowolne przycinanie zdjęcia
* skalowanie
* przesuwanie kadru
* zachowanie wysokiej jakości obrazu

---

### 💾 Zapis zdjęcia

Po zakończeniu edycji zdjęcie można zapisać do galerii urządzenia.

Pliki zapisywane są jako:

```text
Edited_TIMESTAMP.jpg
```

---

## 🧠 Logika działania

### Wybór zdjęć

Aplikacja wykorzystuje:

```kotlin
ActivityResultContracts.GetMultipleContents()
```

do pobierania zdjęć z galerii.

---

### Wczytywanie bitmap

Zdjęcia ładowane są przy użyciu:

```kotlin
ImageDecoder
```

(Android 9+)

oraz:

```kotlin
MediaStore
```

dla starszych wersji Androida.

---

### Przetwarzanie obrazu

Zmiany wykonywane są poprzez:

```kotlin
ColorMatrix
```

oraz:

```kotlin
Matrix
```

co pozwala na:

* zmianę jasności
* zmianę kontrastu
* zmianę nasycenia
* obracanie
* odbijanie obrazu

---

## 🧩 Struktura aplikacji

### MainActivity

Główny punkt wejścia aplikacji.

Odpowiada za:

* uruchomienie Compose
* obsługę galerii
* uruchamianie uCrop

---

### ImageEditorApp()

Główny ekran aplikacji.

Zawiera:

* listę zdjęć
* podgląd edytowanego obrazu
* suwaki regulacji
* przyciski edycji

---

### applyEdits()

Funkcja odpowiedzialna za przetwarzanie obrazu.

Obsługuje:

* brightness
* contrast
* saturation
* rotation
* flip H
* flip V

---

### saveBitmap()

Zapisuje gotowy obraz do galerii urządzenia.

---

### loadBitmapFromUri()

Odpowiada za bezpieczne wczytywanie obrazów z pamięci urządzenia.

---

## 📂 Struktura projektu

```text
app
│
├── MainActivity.kt
│
├── ui
│   └── theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
├── AndroidManifest.xml
│
└── build.gradle.kts
```

---

## 🛠 Technologie

* Kotlin
* Jetpack Compose
* Android SDK
* Material 3
* Coil
* uCrop
* MediaStore
* ImageDecoder
* ColorMatrix
* Gradle Version Catalog

---

## 📌 Możliwe rozszerzenia

* filtry kolorystyczne
* regulacja ostrości
* regulacja rozmycia (Blur)
* cofanie zmian (Undo / Redo)
* porównanie przed / po
* eksport do PNG
* eksport do WebP
* histogram obrazu
* obsługa aparatów urządzenia
* tryb ciemny / jasny
* zapisywanie projektów edycji

---

## 👨‍💻 Autor

Projekt wykonany w Kotlinie z wykorzystaniem Jetpack Compose jako aplikacja do edycji zdjęć na urządzenia z systemem Android.
