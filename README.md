Verkefnið er klassískt slönguspil þar sem tveir leikmenn keppast um að ná efsta reitnum á leikborðinu. Leikurinn er skrifaður í Java og notast við JavaFX til að búa til notendaviðmótið. Maven er notað sem byggingarkerfi og JUnit er notað til að prófa viðskiptalógík (business logic) forritsins.

Til að keyra forritið þarf að vera með JDK 21 og JavaFX sett upp. Verkefnið er sett upp með Maven og hægt er að byggja forritið með skipuninni:

mvn clean package

Eftir það má keyra forritið með run.bat skránni sem fylgir verkefninu. Þar þarf að hafa sett rétta slóð í JAVA_HOME og bæta við JavaFX .jar skrám með module-path og add-modules.

Verkefnið fylgir Maven uppbyggingu með src/main/java fyrir vinnslukóða og viðmót, resources möppu með FXML og myndum, og src/test/java fyrir einingapróf.

JUnit prófanir hafa verið gerðar fyrir leikmann, leikinn sjálfan, slöngur og stiga og tening. Prófanir eru keyrðar með mvn test.

Verkefnið notar einnig observer hönnunarmynstur til að uppfæra viðmót þegar breytingar verða á leikstöðu.

UML klassarit fylgir verkefninu í design/ möppunni. Þar má finna bæði PlantUML skránna og myndina sem sýnir tengsl klasanna og hlutverk þeirra.

Höfundur verkefnisins er Bergur og verkefnið var unnið í tengslum við áfangann HBV202G Software Design and Construction vorið 2025. 
