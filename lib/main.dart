import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  FlutterMethodChannel.instance.configureChannel(); // configure method channel
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  static const platform = MethodChannel('scannerChannel');

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    platform.invokeMethod('configureScanner');
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({Key? key}) : super(key: key);
  static const platform = MethodChannel('scannerChannel');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder(
          stream: DataService.instance.idController.stream,
          builder: (context, snapshot) {
            if (snapshot.data != null) {
              return Center(
                child: Text(
                  snapshot.data ?? '',
                  style: Theme.of(context).textTheme.headline4,
                ),
              );
            }

            return Text(
              "Waiting for new idea",
              style: Theme.of(context).textTheme.headline4,
            );
          }),
      floatingActionButton: FloatingActionButton(onPressed: () {
        platform.invokeMethod('testData');
      }),
    );
  }
}

class FlutterMethodChannel {
  static const channelName =
      'scannerChannel'; // this channel name needs to match the one in Native method channel
  late MethodChannel methodChannel;

  static final FlutterMethodChannel instance = FlutterMethodChannel._init();
  FlutterMethodChannel._init();

  void configureChannel() {
    methodChannel = const MethodChannel(channelName);
    methodChannel.setMethodCallHandler(methodHandler); // set method handler

  }

  Future<void> methodHandler(MethodCall call) async {
    final String id = call.arguments;

    switch (call.method) {
      case "sendData":
        print(id + 'i am from flutter');
        DataService.instance.addIdea(id);
        break;
      default:
        print('no method handler for method ${call.method}');
    }
  }
}

class DataService {
  late StreamController<String> idController;

  static final DataService instance = DataService._init();
  DataService._init() {
    idController = StreamController();
  }

  addIdea(String newIdea) {
    idController.add(newIdea);
  }
}
