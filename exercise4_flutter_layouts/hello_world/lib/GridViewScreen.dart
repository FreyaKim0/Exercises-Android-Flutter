import 'package:flutter/material.dart';

class GridViewScreen extends StatelessWidget{
  @override
  Widget build(BuildContext context){

    return Scaffold(
      body:Container(
        child:GridView(
          children:[
            Container(color:Colors.black45),
            Container(color:Colors.blueGrey),
            Container(color:Colors.black45),
            Container(color:Colors.black38),
          ],
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 2,
            childAspectRatio: 2,
            crossAxisSpacing: 16,
            mainAxisSpacing: 16,
          ),
          padding: EdgeInsets.all(16),
        ),
      )
    );
    /*return Center(
      child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Icon(Icons.ac_unit_rounded),
          ]
      ),
    );*/
  }
}