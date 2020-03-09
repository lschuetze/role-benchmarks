
echo "Removing old files ..."

rm ./lib/ecotj-head.jar
rm ./lib/cp/otredyn.jar ./lib/otredyn_agent.jar ./lib/otre_min.jar

echo "Copying new files ..."

cp /Users/lschuetze/git/ecj-export/ecotj-head.jar ./lib/ecotj-head.jar
cp /Users/lschuetze/git/org.eclipse.objectteams/plugins/org.eclipse.objectteams.otdt/lib/* ./lib/
mv ./lib/otredyn.jar ./lib/cp/otredyn.jar

echo "DONE!"
