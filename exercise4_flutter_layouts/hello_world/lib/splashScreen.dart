import 'package:flutter/material.dart';
import 'nav.dart';

class Splash extends StatefulWidget {
  const Splash({Key? key}) : super(key: key);

  @override
  _SplashState createState() => _SplashState();
}

class _SplashState extends State<Splash>{
  @override
  void initState(){
    super.initState();
    _navigatetohome();
  }

  _navigatetohome()async{
    await Future.delayed(Duration(milliseconds:3000),(){});
    Navigator.pushReplacement(context,MaterialPageRoute(builder: (context)=> Nav()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body:Center(
        child: Container(
          child:Text('Splash Screen',style: TextStyle(color: Colors.blue,fontSize: 24,fontWeight: FontWeight.bold,),)
        ),
      ),
    );
  }
}