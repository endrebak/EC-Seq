-- name: add-gene!!
-- creates a new gene record
-- INSERT INTO RNADB
-- (id, FDR, contrast1, contrast2, contrast3, info)
-- VALUES (:id, :FDR, :contrast1, :contrast2, :contrast3, :info)

-- name: update-user!
-- update an existing user record
-- UPDATE users
-- SET first_name = :first_name, last_name = :last_name, email = :email
-- WHERE id = :id

-- name: get-gene
-- retrieve a gene given the name.
SELECT * FROM toptable
WHERE id = :id

-- name: get-toptable
-- retrieve toptable values given a layer
SELECT * FROM toptable
ORDER BY :col

-- name: delete-gene!
-- delete a gene given the id
-- DELETE FROM RNADB
-- WHERE id = :id
