SHELL := /bin/bash

setup/pre-commit-hook:
	mkdir -p .git/hooks
	cp .githooks/pre-commit .git/hooks/pre-commit
	chmod ug+x .git/hooks/pre-commit
