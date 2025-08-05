# Calculator App 🧮

Basit bir Android hesap makinesi uygulaması. Kotlin ve View Binding kullanılarak geliştirilmiştir.

## 📋 İçindekiler

- [Temel Fonksiyonellik](#temel-fonksiyonellik)
- [Desteklenen İşlemler](#desteklenen-işlemler)
- [Teknik Detaylar](#teknik-detaylar)
- [Tasarım Desenleri](#tasarım-desenleri)
- [Kullanım](#kullanım)
- [Hata Yönetimi](#hata-yönetimi)

## 🚀 Temel Fonksiyonellik

Bu uygulama iki adet sayı girişi alarak, kullanıcının seçtiği matematiksel işlemi gerçekleştiren basit bir hesap makinesidir.

## ⚡ Desteklenen İşlemler

Operation sınıfında bulunan matematiksel işlemler:

- ➕ **Toplama** (add)
- ➖ **Çıkarma** (subtract) 
- ✖️ **Çarpma** (multiply)
- ➗ **Bölme** (divide)

> **Not:** İleride başka işlemler eklemeye açık bir sınıf yapısı bulunmaktadır.

## 🛠️ Teknik Detaylar

### View Binding

**Binding nedir ve neden kullanılıyor?**

Binding değişkeni, View Binding kütüphanesi tarafından oluşturulan bir nesnedir. Bu nesne ile XML dosyasında oluşturduğumuz arayüz elemanlarının ID'lerine daha kompakt bir erişim sağlarız.

**Avantajları:**
- `findViewById()` metodunun karmaşıklığından kurtulma
- Daha güvenli kod yazma
- Derleme zamanında tip kontrolü

### View Binding vs findViewById Karşılaştırması

#### View Binding Versiyonu (Önerilen)
```kotlin
binding.addButton.setOnClickListener {
    val number1Str = binding.number1Input.text.toString()
    // ...
}
```

#### findViewById Versiyonu (Eski Yöntem)
```kotlin
// 1. Önce her elemanı ID'si ile bulup bir değişkene atamak gerekiyor
val number1Input: EditText = findViewById(R.id.number1_input)
val addButton: Button = findViewById(R.id.add_button)

// 2. Sonra bu değişkenler üzerinden işlem yapılıyor
addButton.setOnClickListener {
    val number1Str = number1Input.text.toString()
    // ...
}
```

## 🎯 Tasarım Desenleri

### Strategy Pattern

Calculator değişkeni **Strategy Pattern** kullanmaktadır. Bu aynı zamanda SOLID prensiplerinden **Dependency Inversion Principle**'a uygun bir durumdur.

**Neden Strategy Pattern?**
- MainActivity, somut Calculator sınıfına doğrudan bağımlı değildir
- Soyut olan Calculation arayüzüne bağımlıdır
- Her hesaplama algoritması ayrı bir sınıfa konulmuştur
- Sınıfların nesneleri birbirinin yerine kullanılabilir

**Gelecekteki Genişletme:**
```kotlin
// Yeni bir hesaplama yöntemi eklemek için
class ScientificCalculator : Calculation {
    // Bilimsel hesaplama implementasyonu
}

// MainActivity'de tek satır değişiklik yeterli
val calculator: Calculation = ScientificCalculator()
```

## 💻 Kullanım

### Button Click Listener Örneği

```kotlin
binding.addButton.setOnClickListener {
    val number1Str = binding.number1Input.text.toString()
    // İşlem mantığı burada
}
```

**Açıklama:**
- `binding`: activity_main.xml ile MainActivity.kt arasında köprü görevi görür
- `addButton`: XML'deki `android:id="@+id/add_button"` elemanına karşılık gelir
- `setOnClickListener`: Butona tıklanma olayını dinler ve kod bloğunu çalıştırır

## 🛡️ Hata Yönetimi

### Validation Kontrolleri

`performOperation()` metodunda aşağıdaki durumlar kontrol edilmektedir:

#### 1. Boş Girdi Kontrolü
```kotlin
if (number1Str.isEmpty() || number2Str.isEmpty()) {
    // Hata mesajı gösterimi
}
```

#### 2. Geçersiz Karakter Kontrolü
```kotlin
if (number1 == null || number2 == null) {
    // Sayı dışında karakter girişi kontrolü
}
```

#### 3. Sıfıra Bölme Kontrolü
```kotlin
if (number2 == 0.0) {
    Result.failure(...)
}
```

### toDoubleOrNull() vs toDouble()

**Neden `toDoubleOrNull()` kullanılmış?**

- `toDouble()`: Geçersiz girdi durumunda exception fırlatır
- `toDoubleOrNull()`: Geçersiz girdi durumunda `null` döner, güvenli dönüşüm sağlar

```kotlin
val number = "abc".toDoubleOrNull() // null döner, hata vermez
val number2 = "abc".toDouble()      // Exception fırlatır!
```

### 🔧 İyileştirme Önerileri

Validation'ları daha etkili hale getirmek için:

- Tüm doğrulama mantığını MainActivity'den çıkarıp ayrı bir sınıfa taşıma
- SOLID prensiplerini daha da ileriye taşıma
- Daha modüler bir yapı elde etme

```kotlin
class InputValidator {
    fun validateInputs(input1: String, input2: String): ValidationResult {
        // Validation logic here
    }
}
```