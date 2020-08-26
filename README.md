# Soundpool API Demo

This is an experiment to create a simple loading manager for the Soundpool API in Android, which handles the process of loading and playing sounds.

Soundpool requires all sounds files to be 'loaded' before they can be used.

This loading process creates a 'delay' between when an app is opened, and when the user can start using the app and its sounds.

The goal of this project is to architect a basic app with a manager class to handle the loading process, and only allow the user to use the app when all the sounds are loaded. 

This project includes only a few wav files for testing, so the loading process may seen quick.

However, with older versions of Android and with more files to load the delay becomes more noticeable.
