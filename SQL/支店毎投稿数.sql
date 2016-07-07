select count(posts.text) as count 
from users inner join posts on users.id = posts.user_id
where branch_id = (select branch_id from users where id = 24);