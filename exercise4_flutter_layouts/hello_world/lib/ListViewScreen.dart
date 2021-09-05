import 'package:flutter/material.dart';

class ListViewScreen extends StatelessWidget{

  @override
  Widget build(BuildContext context){
    return ListView(
        padding: const EdgeInsets.all(8),
        children:<Widget>[
          Card(child:ListTile(
              title:Text('Super man'),
              subtitle:Text('Lorem ipsum dolor sit amet, ad iudico petentium.'),
              leading: CircleAvatar(backgroundImage: AssetImage("assets/images/superman.JPG"),),
              trailing: Icon(Icons.star))
          ),
          Card(child:ListTile(
            title:Text('Bat man'),
            subtitle:Text('id mea elit utroque menandri.'),
            leading: CircleAvatar(backgroundImage: AssetImage("assets/images/batman.JPG"),),)
          ),
          Card(child:ListTile(
            title:Text('Hawk eye'),
            subtitle:Text('no, laudem antiopam quaerendum ex pro. Usu adipisci electram interpretaris'),
            leading: CircleAvatar(backgroundImage: AssetImage("assets/images/whoisthis.JPG"),),trailing: Icon(Icons.star),)
          ),
        ]
    );
  }
}