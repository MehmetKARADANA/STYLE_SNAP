🎨 Style Snap

Style Snap, kullanıcıların fotoğraflarını seçip hazır stil transfer modelleriyle (örneğin `Candy`) sanat eseri gibi dönüştürebildiği bir Android uygulamasıdır. Uygulama TensorFlow Lite (TFLite) kullanılarak geliştirilmiştir.

---

## 🖼️ Özellikler

* 📸 Galeriden veya kameradan fotoğraf seçme
* 🎨 Stil seçimi (şu anda `Candy` mevcut)
* 🧠 TFLite modeli ile stil transferi
* 💾 Stilize edilmiş görseli galeriye kaydetme
* ⚡ Anlık stil uygulama deneyimi

---

## 🔧 Teknolojiler

* Jetpack Compose
* Kotlin
* TensorFlow Lite (tflite)
* ViewModel & StateFlow
* Coroutine

---

## 🚀 Kurulum

1. Bu projeyi klonlayın:

```bash
git clone https://github.com/kullaniciadi/StyleSnap.git
```

2. Android Studio ile açın.

3. Aşağıdaki dizine TFLite stil modelinizi ("candy.tflite") ekleyin:

```
app/src/main/assets/
```

4. Uygulamayı çalıştırın.

---

## 📂 Stil Modelleri

Proje şu anda sadece `candy.tflite` modelini desteklemektedir. Diğer modeller için aşağıdaki kaynakları inceleyebilirsiniz:

* TensorFlow Lite örnek stil modelleri:
  🔗 [https://github.com/tensorflow/examples/tree/master/lite/examples/style\_transfer/android](https://github.com/tensorflow/examples/tree/master/lite/examples/style_transfer/android)

> Dilerseniz bu projeye daha fazla model ekleyebilir, dropdown veya yatay scroll yapısıyla birden fazla stil sunabilirsiniz.

---

## 🧠 Ekran Görüntüsü

 <img width="1080" height="2424" alt="Screenshot_20250719_014447" src="https://github.com/user-attachments/assets/70321428-acbf-4a86-97f6-77f412069de8" />
<img width="1080" height="2424" alt="Screenshot_20250719_014523" src="https://github.com/user-attachments/assets/e2a131c4-803e-4044-a85c-8e8ee80fc9ba" />


---

## 📌 Notlar

* Uygulama yalnızca `.tflite` formatındaki modellerle çalışır.
* TensorFlow Lite modelleri quantized (küçültülmüş) olmalıdır.

---

## 📬 Geliştirici

Mehmet Karadana

---

## 📄 Lisans

Bu proje MIT lisansı ile lisanslanmıştır.
