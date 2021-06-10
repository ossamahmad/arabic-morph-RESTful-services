# arabic-morph-RESTful-services

There are three REST services in this project:
1- /analyse : which returns the analysis results list of a tokenized arabic text.
    These results come from several levels of treatment. firsrt of all of the segementation, then the segments are compared to the list of exceptional words, proper nouns, tool words and finally the possible nominal and verbal solutions are considered.
    The result is returned as a json file
    
2- /stems : Given an arabic text this method returns a list of possible sgementations in three elements (prefix+stem+suffixe). 
    The returned list consists of a json file.
    
3- /roots : Given an arabic text this method returns a list of possible roots of the words of the text.
    The results is a json file
    
The alication uses the the برنامج الخليل الصرفي https://ossl.alecso.org/affich_oso_details.php?id=57 
