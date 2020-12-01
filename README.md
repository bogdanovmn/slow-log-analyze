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
Dec  1 00:15:06 syslogd[54]: Configuration Notice:
... 21h 59m 59s ...
Dec  1 22:15:05 syslogd[54]: ASL Sender Statistics

...

Dec  1 22:16:49 Google Chrome Helper[554]: Libnotify: notify_register_coalesced_registration failed with code 9 on line 2835
... 2m 9s ...
Dec  1 22:18:58 Google Chrome Helper[2616]: Libnotify: notify_register_coalesced_registration failed with code 9 on line 2835

[*] Log statistic:
Total lines: 263, events: 233, skipped: 30


```

Here, **skipped** - lines without recognized timestamp. It may be ok, for instance, lines of stacktrace value. Or it may be not ok, for instance, if default timestamp definition doesn't fit and you have to play with additional options of the tool. 