# X_Tractor

APIs Used :
1. OpenCV
2. Tess4j (Java Wrapper of Tesseract OCR)
3. PDFbox
4. Webcam Capture


What it does ?
A GUI Java application with 2 motives-
1. Start Webcam and convert the image captured by it into text.
2. Allows importing pdf files from your PC and then extract text from it.


How is it useful?
1. Copy whatever text from your Mobile Phone browser into clipboard of your PC.
2. Easy to import pdf files and get the text out of it.


How Accurate is it?
Well the accuracy is an issue here. Although most of the time it performs well but because of the poor camera resolution on my PC, it gives out some random shit too some times.
The issue here is ImageQuality and to manage that OpenCV is used to perform some image enhancements to improve accuracy of the OCR.


How OpenCV works?
First the Captured image is converted into GrayScale i.e Black and white format. After that Gaussian Blur and adpative threshold techniques are used to remove noise.



Thats All folks. 😉
