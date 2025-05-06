<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron"
    xmlns:b="http://www.mycompany.com/bewerber"
    queryBinding="xslt2">
    <pattern>
        <rule context="bewerber[Land = 'DEUTSCHLAND']">
            <assert test="Strasse and HausNr and PLZ">
                Straße, Hausnummer und PLZ sind erforderlich!
            </assert>
        </rule>
    </pattern>
</schema>
