import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_world/ListViewScreen.dart';
import 'package:hello_world/GridViewScreen.dart';
import 'package:hello_world/HeroScreen.dart';

class Nav extends StatefulWidget{
  @override
  _NavState createState()=> _NavState();
}

class _NavState extends State<Nav>{
  int _selectedIndex=0;
  List<Widget> _widgetOptions = <Widget>[
    ListViewScreen(),
    GridViewScreen(),
    HeroScreen(),
  ];

  void _onItemTap(int index){
    setState((){
      _selectedIndex= index;
    });
  }

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(title:Text('App Bar')),
              body:Center(
                child: _widgetOptions.elementAt(_selectedIndex)
              ),
              bottomNavigationBar: BottomNavigationBar(
                items: const <BottomNavigationBarItem>[
                  BottomNavigationBarItem(
                      icon: Icon(Icons.home),
                      title:Text('ListView')),
                  BottomNavigationBarItem(
                      icon: Icon(Icons.person),
                      title:Text('GridView')),
                  BottomNavigationBarItem(
                      icon: Icon(Icons.message),
                      title:Text('Hero / navigator')),
                ],
                currentIndex: _selectedIndex,
                onTap: _onItemTap,
              ),
    );
  }
}