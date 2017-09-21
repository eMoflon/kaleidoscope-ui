# kaleidoscope-ui
UI components for kaleidoscope, a model synchronisation framework.

## Prerequisite:

1. Install Eclipse Oxygen with Eclipse Modeling Tools (tested on 4.7.0) http://www.eclipse.org/downloads/packages/eclipse-modeling-tools/oxygenr

## Install required plugins:
1. Install the latest version of Xtend: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
2. Install eMoflon: http://emoflon.github.io/eclipse-plugin/emoflon_2.31.0/updatesite
Make sure you enable "contact all update sites" in the update manager so all dependencies are automatically installed.

## Setup steps:

1. Check encoding for Xtend Files
  - In Eclipse: Go to ```Window->Preferences->General->Workspace```
  - Change the text file encoding to 'Other: UTF-8'
2. Go to ```File/Import.../Team/Team Project Set```, check URL and enter in and import this PSF file: https://raw.githubusercontent.com/eMoflon/kaleidoscope-core/master/projectSet.psf
