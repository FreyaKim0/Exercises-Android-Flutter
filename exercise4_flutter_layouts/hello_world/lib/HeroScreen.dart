import 'package:flutter/material.dart';

class HeroScreen extends StatelessWidget{

  @override
  Widget build(BuildContext context){
    return Scaffold(
     body:Center(
       child:Hero(
          tag:'hero_item',
          child:Container(
             width: 100,
             height: 100,
             color: Colors.blueAccent,
         ),
       ),
     ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context)=>SecondRoute()),
          );
        },
        child: Icon(Icons.arrow_circle_down),
      ),
    );
  }
}

class SecondRoute extends StatelessWidget{
  @override
  Widget build(BuildContext context){
    return Scaffold(
      body: Center(
        child:Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children:[
            Hero(
              tag:'hero_item',
              child:
                Container(
                  width: 100,
                  height: 500,
                  color: Colors.blueAccent,
                ),
              ),
          ],
        )
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          Navigator.pop(context);
        },
        child:Icon(Icons.arrow_back),
      ),
    );
  }
}