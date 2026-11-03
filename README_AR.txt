مشروع يلا كورة - إضافة استقبال إشعارات Firebase Cloud Messaging (FCM)

ما أُضيف:
- صلاحية إشعارات Android 13+ وطلب الإذن عند فتح التطبيق.
- خدمة MyFirebaseMessagingService لاستقبال وعرض الإشعارات.
- اشتراك تلقائي بموضوع FCM: yalla_kora_all.
- مكتبة Firebase Messaging وGoogle Services Gradle plugin.

مهم قبل البناء:
1) أضف ملف google-services.json الخاص بتطبيق Firebase المسجل بالحزمة EXACTLY: com.yallkora.com
   وضعه داخل app/google-services.json.
2) شغّل GitHub Actions لبناء APK.
3) الإرسال الفعلي يحتاج جهة إرسال موثوقة تستخدم Firebase Admin SDK أو HTTP v1؛ لا تضع مفتاح الخدمة داخل APK أو HTML.

ملاحظة: المشروع يستخدم واجهة index.html الحالية دون تعديلها. هذه إضافة الاستقبال فقط، وليست خدمة إرسال أو نظام جدولة إشعارات.


تحديث Android 13: تم استخدام ActivityResultLauncher بلغة Java عبر ComponentActivity لطلب POST_NOTIFICATIONS، مع الإبقاء على WebView وFCM والاشتراك بالموضوع yalla_kora_all كما هي.
