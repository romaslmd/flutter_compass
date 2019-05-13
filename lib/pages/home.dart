import 'package:flutter/material.dart';

import "./../compass.dart";

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The FluprimarySwatchtter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
        appBar: AppBar(
          // Here we take the value from the MyHomePage object that was created by
          // the App.build method, and use it to set our appbar title.
          title: Text("Compass"),
        ),
        body: Center(
          // Center is a layout widget. It takes a single child and positions it
          // in the middle of the parent.
            child: Container(
                alignment: Alignment.center,
                height: 300,
                child: Flex(
                    direction: Axis.vertical,
                    children: <Widget>[
                      Container(
                          width: 20,
                          margin: EdgeInsets.only(bottom: 5.0),
                          decoration: BoxDecoration(
                              border: Border(
                                top: BorderSide(width: 10.0, color: Colors.transparent),
                                bottom: BorderSide(width: 10.0, color: Colors.red),
                                left: BorderSide(width: 10.0, color: Colors.transparent),
                                right: BorderSide(width: 10.0, color: Colors.transparent),
                              )
                          )
                      ),
                      Container(
                          width: 250,
                          height: 250,
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.all(Radius.circular(250.0))
                          ),
                          child: Compass()
                      )
                    ]
                )
            )
        )
    );
  }
}
