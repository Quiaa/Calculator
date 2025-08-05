1.a) Bu uygulama ne yapıyor? Temel fonksiyonelliği nedir?
1.a.cevap) Basit bir hesap makinesi uygulaması. İki adet input alarak(sayı), kullanıcının seçtiği matematiksel işlemi gerçekleştirme fonksiyonuna sahip.

1.b) Kaç tane matematik işlemi destekliyor ve bunlar nelerdir?
1.b.cevap) Operation classında bulunan işlemleri gerçekleştirebiliyor. Bunlar toplama(add), çıkarma(subtract), çarpma(multiply) ve bölme(divide) şeklinde. İleride başka işlemler eklemeye açık bir sınıf yapısı bulunmaktadır.

1.c) Binding nedir ve neden kullanılıyor?
1.c.cevap) Binding değişkeni, View Binding kütüphanesi tarafından oluşturulan bir nesnedir. Bu nesne ile XML dosyasında oluşturduğumuz arayüz elemanlarının ID lerine daha kompakt bir erişim sağlarız. Binding kullanılarak, javada kullanılan eski usul findViewById() metodunun karmaşıklığından kurtuluruz.

1.d) Calculator değişkeni hangi tasarım desenini (desing pattern) kullanıyor?
1.d.cevap) Strategy Pattern kullanılmıştır, aynı zamanda SOLID prensiplerinden Dependency Inversion Principle yani bağımlılıkların tersine çevirilmesi prensibine uygun bir durumdur çünkü MainActivity, somut Calculator sınıfına doğrudan bağımlı değildir. Bunun yerine, soyut olan Calculation arayüzüne bağımlıdır.
Strategy Patternin amacı, her birini ayrı bir sınıfa koymak ve bu sınıfların nesnelerini birbirinin yerine kullanılabilir yapmaktır. Bizim uygulamamızda Calculation arayüzü hesaplama yapma özelliğini tanımlarken bu hesaplamayı somut hale dönüştüren Calculator sınıfıdır.
Eğer gelecekte farklı bir hesaplama yöntemi (örneğin bilimsel hesaplama) gerekirse, ScientificCalculator adında yeni bir sınıf oluşturup MainActivity'deki tek bir satırı değiştirerek tüm hesaplama stratejisini değiştirebiliriz.

2.a) binding.addButton.setOnClickListener {} satırında: binding ne işe yarar? addButton XML'deki hangi elemana karşılık gelir? setOnClickListener neden kullanılır?
2.a.cevap) binding nesnesi, activity_main.xml layout dosyası ile MainActivity.kt Kotlin dosyası arasında köprü görevi görüyor. View Binding kütüphanesi tarafından otomatik olarak oluşturulan bir nesne ve layout dosyasındaki android:id'si olan her bir elemanın (buton, metin alanı vb.) 
doğrudan bir referansını içinde barındırır. Kısacası, XML'deki görsel elemanlara koddan güvenli bir şekilde ulaşmamızı sağlayan bir anahtar veya bir "adres defteri" denilebilir.
 <Button   android:id="@+id/add_button" elemanı View Binding kütüphanesi aracılığıyla addButton nesnesi haline gelir.
setOnClickListener, bir butona (veya herhangi bir "tıklanabilir" arayüz elemanına) bir "dinleyici" atamak için kullanılır. Bu "dinleyici", sürekli olarak "Bu butona tıklandı mı?" diye kontrol eder. 
Kullanıcı butona tıkladığı anda, dinleyici bunu fark eder ve {} süslü parantezleri içine yazdığınız kod bloğunu çalıştırır.

2.b) View Binding yerine findViewById kullandığımızda kod nasıl değişirdi? Örnek gösterin.
2.b.cevap) Eğer View Binding kullanmasaydık, her bir arayüz elemanına teker teker, findViewById metodunu kullanarak "manuel" olarak ulaşmamız gerekirdi. Bu da her View için teker teker değişken oluşturmamızı ve sonradan bağlamamızı gerektirirdi. Bunun yerine View Binding kullanarak iki adımı birlikte gerçekleştiriyoruz.

// --- View Binding Versiyonu (Kısa ve Güvenli) ---
binding.addButton.setOnClickListener {
    val number1Str = binding.number1Input.text.toString()
    // ...
}

// --- findViewById Versiyonu (Daha Uzun ve Riskli) ---
// 1. Önce her elemanı ID'si ile bulup bir değişkene atamak gerekiyor.
val number1Input: EditText = findViewById(R.id.number1_input)
val addButton: Button = findViewById(R.id.add_button)

// 2. Sonra bu değişkenler üzerinden işlem yapılıyor.
addButton.setOnClickListener {
    val number1Str = number1Input.text.toString()
    // ...
}

3) Error Handling ve Validation
performOperation() metodunda error handling nasıl yapılmış?
a)Hangi durumlar kontrol ediliyor?
3.a.cevap) if (number1Str.isEmpty() || number2Str.isEmpty()) { ... } kodu ile girdilerin boş olup olmadığı kontrol ediliyor.
if (number1 == null || number2 == null) { ... } kodu ile kullanıcının metin alanlarına sayı yerine "abc" gibi harf veya geçersiz karakterler girip girmediği kontrol edilir. Butonlarımıza özellik olarak sadece decimal girilebilir olarak ekledik ama her ihtimale karşı kontrol sağlıyoruz.
if (number2 == 0.0) {
    Result.failure(...)
} Bölme işlemi yapılacağı zaman paydanın 0 olma durumu kontrol ediliyor.

b)toDoubleOrNull() neden kullanılmış, toDouble() yerine?
3.b.cevap) toDouble() kullanılsaydı input olarak sayı olmayan bir değer girildiği zaman bu değeri double a çeviirmeye çalışırken hata ver oluşacaktı. Biz inputları her ihtimale karşı decimal olarak alıyor olsak da oluşabilecek herhangi bir sorunun önüne geçebilmek adına toDoubleOrNull kullanarak
double a çevrilemeyen girdilerin nulla çevrilerek hata kontrolüne girmesini sağlıyoruz. Her ihtimale karşı önlem almış oluyoruz.

c)Bu validation'ları daha etkili hale getirmek için ne önerebilirsiniz?
3.c.cevap) SOLID prensiplerini daha da ileriye taşımak için, tüm doğrulama mantığını MainActivity'den çıkarıp kendi sınıfına taşıyabiliriz. Bu şekilde daha moduler bir yapı elde etmiş oluruz.
