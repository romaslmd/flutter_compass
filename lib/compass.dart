import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Compass extends StatefulWidget {
  @override
  _CompassState createState() => _CompassState();
}

class _CompassState extends State<Compass> {
  static final String channel = "com.ancient.dev.flutter_compass/compass";
  static EventChannel stream = EventChannel(channel);

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
      initialData: 0.0,
      stream: stream.receiveBroadcastStream(),
      builder: _render
    );
  }

  Widget _render(context, snapshot) {
    double azimuth = snapshot.data;

    return Transform.rotate(
      angle: azimuth,
      child: Image.asset('assets/images/compass.png')
    );
  }
}
