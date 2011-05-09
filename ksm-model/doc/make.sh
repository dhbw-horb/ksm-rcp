#!/bin/sh
asciidoc -v "KSM-Datamodel.asciidoc" &&
    a2x -v "KSM-Datamodel.asciidoc" --dblatex-opts "-P latex.output.revhistory=0 -P latex.class.options=14pt -P latex.class.article=scrartcl -s asciidoc-dblatex.sty"
