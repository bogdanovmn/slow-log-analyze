# 1. Build the project in order to get an executable JAR
```bash
mvn clean package
```
# 2. Check the artifact / read usage help
```bash
java -jar slow-log-analyzer-cli/target/slow-log-analyzer.jar -h
```

# 3. Usage example 
The next command shows log's events with duration above than 60 seconds  
```bash
java -jar slow-log-analyzer.jar -l /var/log/system.log -t 60 -tpos 3
```
The **-tpos** argument just helps to find a timestamp value

The result on my laptop:
```log
[51299 sec] Nov 16 00:15:04 xpcproxy[9777]: libcoreservices: _dirhelper_userdir: 529: bootstrap_look_up returned (ipc/send) invalid destination port
[29527 sec] Nov 16 14:30:03 com.apple.xpc.launchd[1] (com.apple.bsd.dirhelper[9784]): Endpoint has been activated through legacy launch(3) APIs. Please switch to XPC or bootstrap_check_in(): com.apple.bsd.dirhelper
[528 sec] Nov 16 23:03:32 systemstats[54]: assertion failed: 18F132: systemstats + 673867 [0A10FDEF-5C7C-3D70-97F1-3A1D67836242]: 0x5
[543 sec] Nov 16 23:13:32 systemstats[54]: assertion failed: 18F132: systemstats + 673867 [0A10FDEF-5C7C-3D70-97F1-3A1D67836242]: 0x5
[472 sec] Nov 16 23:25:26 com.apple.xpc.launchd[1] (com.apple.imfoundation.IMRemoteURLConnectionAgent): Unknown key for integer: _DirtyJetsamMemoryLimit
[590 sec] Nov 16 23:33:42 systemstats[54]: assertion failed: 18F132: systemstats + 673867 [0A10FDEF-5C7C-3D70-97F1-3A1D67836242]: 0x5

[*] Log statistic:
Total lines: 106, events: 66, skipped: 40

```

Here, **skipped** - lines without recognized timestamp. It may be ok, for instance, lines of stacktrace value. Or it may be not ok, for instance, if default timestamp definition doesn't fit and you have to play with additional options of the tool. 