Pressure
========

Сохраняем показания артериального давления
------------------------------------------

***


Используется H2 база данных

```bash
#!/bin/sh

dir=$(dirname "$0")

#java -cp "$dir/h2-2.3.230.jar:$H2DRIVERS:$CLASSPATH" org.h2.tools.Console "$@"
java -cp "$dir/h2-2.3.230.jar" org.h2.tools.Server -baseDir ~/h2db
```

Небольшой [плейлист по сервлетам](https://youtube.com/playlist?list=PL7Bt6mWpiizZq71c4wuBl7lmY-M7nen_J&si=whywvWPxQoBf-WMQ)

