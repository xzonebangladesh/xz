# My Hide App — Android প্রোটোটাইপ

এটি একটি ছোট Android অ্যাপ: একটি লেখা এবং `Hide App` বাটন। বাটনে চাপ দিলে Android PackageManager দিয়ে অ্যাপের launcher activity disable করার চেষ্টা করে। ফোনের launcher অনুযায়ী আইকন লুকানো নাও হতে পারে।

## গুরুত্বপূর্ণ সীমাবদ্ধতা
- Android-এর সব ফোন/launcher একইভাবে এই আচরণ সমর্থন করে না।
- আইকন Hide হলে অ্যাপটি সাধারণ launcher থেকে আর খোলা নাও যেতে পারে।
- অ্যাপটি Settings → Apps-এ ইনস্টল থাকা উচিত, তবে launcher component পুনরায় enable করার সহজ UI এই প্রোটোটাইপে নেই।
- পরীক্ষার আগে অতিরিক্ত গুরুত্বপূর্ণ ডেটা বা কাজের জন্য ব্যবহার করবেন না।
- এটি কোনো permission চায় না এবং ব্যাকগ্রাউন্ডে গোপন কাজ করে না।

## GitHub দিয়ে APK তৈরি
1. এই ZIP extract করুন।
2. GitHub-এ একটি নতুন repository তৈরি করুন।
3. ZIP-এর ভেতরের ফাইলগুলো repository-তে upload/commit করুন (ফোল্ডারের ভেতরের ফাইলগুলো, ZIP নিজে নয়)।
4. `Actions` ট্যাবে যান। `Build Android APK` workflow সফল হলে run খুলুন।
5. `Artifacts` থেকে `MyHideApp-debug-apk` ডাউনলোড করুন। ZIP artifact-এর মধ্যে `app-debug.apk` থাকবে।
6. APK ইনস্টল করার আগে Android-এ আপনার browser/file manager-কে “Install unknown apps” অনুমতি দিতে হতে পারে।

## Package name
`com.example.myhideapp`
