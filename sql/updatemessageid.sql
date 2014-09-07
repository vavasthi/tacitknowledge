create procedure update_message_id()
begin
begin transaction
update UsenetPost_UsenetPostReference set UsenetPost_id=replace(replace(UsenetPost_id,"<",""),">","");
update UsenetPost set id=replace(replace(id,"<",""),">","");
update UsenetPostReference set referenceId=replace(replace(referenceId,"<",""),">","");
end transaction
end
